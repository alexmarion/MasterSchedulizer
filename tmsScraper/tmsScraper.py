#!/usr/bin/python
import os
from bs4 import BeautifulSoup
import urllib2
import re
import csv
import threading

baseUrl = 'https://duapp2.drexel.edu'
TMSDirPath = "TMS/"

def main():
	# Make a new directory for TMS data
	if not os.path.exists(TMSDirPath):
		os.makedirs(TMSDirPath)

	# Get the initial select term page
	appUrl = 'https://duapp2.drexel.edu/webtms_du/app'
	selectTermPage = urllib2.urlopen(appUrl)
	selectTermPageSoup = BeautifulSoup(selectTermPage.read())

	# Get the list of quarters from the select term page
	termPanelSoup = selectTermPageSoup.find("table", {"class":"termPanel"})
	quartersSoup = termPanelSoup.find_all("div", {"class":"term"})

	# Get the url to each quarter
	# Store the url in a dictionary key quarter id
	quarterUrls = {}
	quarterUrls = getQuarterUrls(quartersSoup, quarterUrls)
	
	# Read each quarter page into soup
	for key in quarterUrls:
		quarterDirPath = TMSDirPath + key + '/'
		# Make a new folder for each quarter
		
		if not os.path.exists(quarterDirPath):
			os.makedirs(quarterDirPath)

		quarterPage = urllib2.urlopen(quarterUrls[key])
		quarterSoup = BeautifulSoup(quarterPage.read())

		# Get the url to each college in each quarter
		collegeLinksSoup = quarterSoup.find("div", {"id":"sideLeft"}).find_all("a")
		for link in collegeLinksSoup:
			collegeUrl = baseUrl + "".join(link.get("href"))

			# Get the each college page
			collegePage = urllib2.urlopen(collegeUrl)
			collegePageSoup = BeautifulSoup(collegePage.read())

			# Get the link to each subject page
			oddSubjectDivs = collegePageSoup.find_all("div", {"class":"odd"})
			evenSubjectDivs = collegePageSoup.find_all("div", {"class":"even"})

			subjectUrls = []
			for div in oddSubjectDivs:
				subjectUrls.append(baseUrl + "".join(div.find("a").get("href")))

			for div in evenSubjectDivs:
				subjectUrls.append(baseUrl + "".join(div.find("a").get("href")))
			
			# Open each subject page and get every course
			for url in subjectUrls:
				subjectPage = urllib2.urlopen(url)
				subjectPageSoup = BeautifulSoup(subjectPage.read())

				evenCoursesSoup = subjectPageSoup.find_all("tr", {"class":"even"})
				oddCoursesSoup = subjectPageSoup.find_all("tr", {"class":"odd"})

				courseList = {}
				courseList = getCourseList(evenCoursesSoup, courseList)
				courseList = getCourseList(oddCoursesSoup, courseList)

				for key in courseList:
					# Create a filepath for each subject
					courseFilePath = quarterDirPath + key + '.csv'
					# Create a file for each subject
					courseFile = open(courseFilePath, 'wb')
					# Create a csv writer to write the courses to the subject file
					wr = csv.writer(courseFile, dialect = 'excel')
					for row in courseList[key]:
						wr.writerow([s.encode('UTF8') for s in row])
						


def getQuarterUrls(quartersSoup, quarterUrls):
	# Get the url to each quarter
	# Store the url in a dictionary key quarter id
	for quarter in quartersSoup:
		quarterID = "".join(quarter.find("a", href = True).contents)
		quarterUrl = baseUrl + "".join(quarter.find("a").get("href"))
		quarterUrls[quarterID] = quarterUrl
	return quarterUrls

def getCourseList(coursesSoup, courseList):
	for courseSoup in coursesSoup:
		courseFields = courseSoup.find_all("td")
		depth = len(courseFields[0].find_parents("td"))
		# Ignore subtables 
		if depth == 2:
			subjectCode = "".join(courseFields[0].contents)
			courseNum = "".join(courseFields[1].contents)

			# If the dictionary already contains an instance of the class, skip it
			skip = False
			if subjectCode in courseList:
				for cl in courseList[subjectCode]:
					for course in cl:
						if courseNum in course:
							skip = True
							print subjectCode + " " + courseNum + " already exists"
							break
					if skip:
						break
			if skip:
				continue

			courseTitle = "".join(courseFields[6].contents)

			# Get the credits, preReqs, and coReqs for each course
			crnUrl = baseUrl + "".join(courseFields[5].find("a").get("href"))
			coursePage = urllib2.urlopen(crnUrl)
			coursePageSoup = BeautifulSoup(coursePage.read())

			discPanelSoup = coursePageSoup.find("table", {"class":"descPanel"})
			# If no description panel go to the next url
			try: 
				subPoints = discPanelSoup.find_all("div", {"class":"subpoint"})
			except AttributeError:
				continue

			credits = ""
			preReqs = ""
			coReqs = ""

			for subPoint in subPoints:
				section = "".join(subPoint.b.contents)
				# Get the credits
				if section == "Credits:":
					credits = "".join(subPoint.contents[1]).strip()

				# Get the preReqs
				spanRemovedFlag = False
				if section == "Pre-Requisites:":
					if len(subPoint.contents) > 1 and subPoint.span is not None:
						preReqList = subPoint.find_all("span")
						preReqStr = ""
						for preReq in preReqList:
							# Strip grade requrements and parenthesis from string
							preReqStr = "".join(preReq.contents).strip()

							# Remove placement exams as preReqs
							if "Placement Exam" in preReqStr:
								spanRemovedFlag = True
								continue
							# If a preReq has been removed, remove the instance
							# of "and" or "or" from the next preReq
							if spanRemovedFlag:
								preReqStr = re.sub(r'(and|or)', '', preReqStr)
								spanRemovedFlag = False	

							preReqStr = re.sub(r' Minimum Grade: [A-Z]', '', preReqStr).strip()
							preReqStr = re.sub(r'(\(|\))', '', preReqStr).strip()
							preReqStr = re.sub(r' \-', '', preReqStr)

							# Remove the space between the subject code and the
							# course number
							pattern = re.compile(r'([A-Z] ([0-9]|[A-Z]))')
							tempMatch = re.search(pattern, preReqStr)
							if tempMatch is not None:
								tempMatch = "".join(tempMatch.group(1))
								tempMatch = "".join(tempMatch.split())
								preReqStr = re.sub(pattern ,tempMatch, preReqStr)

							preReqs = preReqs + " " + preReqStr
						preReqs = preReqs.strip()

				# Get the coReqs
				if section == "Co-Requisites:":
					if len(subPoint.contents) > 1 and subPoint.span is not None:
						coReqList = subPoint.find_all("span")
						coReqStr = ""
						for coReq in coReqList:
							# Strip  parenthesis from string
							coReqStr = "".join(coReq.contents).strip()
							coReqStr = re.sub(r'(\(|\))', '', coReqStr).strip()

							# Remove the space between the subject code and the
							# course number
							pattern = re.compile(r'([A-Z] ([0-9]|[A-Z]))')
							tempMatch = re.search(pattern, coReqStr)
							if tempMatch is not None:
								tempMatch = "".join(tempMatch.group(1))
								tempMatch = "".join(tempMatch.split())
								coReqStr = re.sub(pattern ,tempMatch, coReqStr)

							coReqs = coReqs + " " + coReqStr
						coReqs = coReqs.strip()

			# Load course information into list with the following format:
			# subjectCode,courseNum,courseTitle,credits,preReqs,coReqs,term
			course = []
			course = [subjectCode, courseNum, courseTitle,credits, preReqs, coReqs, "TERMS-OFFERED", '0']

			print course

			if subjectCode in courseList:
				courseList[subjectCode].append(course)
			else:
				courseList[subjectCode] = [course]

	return courseList

if __name__ == "__main__": main()
