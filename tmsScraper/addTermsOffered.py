#!usr/bin/python
import csv
import os

tmsDirPath = "TMS/"
termsOfferedPath = "termsOfferedList.csv"

def main():
	courseList = {}
	# Open terms offered document
	with open(termsOfferedPath, 'rb') as f:
		reader = csv.reader(f)
		for row in reader:
			courseInfo = row[0].split()
			subjectCode = courseInfo[0]
			courseNum = courseInfo[1]
			termsOffered = row[2]
			print subjectCode
			for dirName, subDirList, fileList in os.walk(tmsDirPath):
				for fname in fileList:
					if fname.strip(".csv") == subjectCode:
						fpath =  dirName + "/" + fname
						with open(fpath, 'rb') as subjectFile:
							subjectReader = csv.reader(subjectFile)
							for course in subjectReader:
								if course[1] == courseNum:
									course[6] = termsOffered
									if subjectCode in courseList:
										if course not in courseList[subjectCode]:
											courseList[subjectCode].append(course)
									else:
										courseList[subjectCode] = [course]
									print courseList
									
	for key in courseList:
		# Create a filepath for each subject
		courseFileDir = tmsDirPath + "MASTERLIST/"
		courseFilePath = courseFileDir + key + ".csv"
		# Make a new directory for TMS data
		if not os.path.exists(courseFileDir):
			os.makedirs(courseFileDir)
		print courseFilePath
		# Create a file for each subject
		courseFile = open(courseFilePath, 'wb')
		# Create a csv writer to write the courses to the subject file
		wr = csv.writer(courseFile, dialect = 'excel')
		for row in courseList[key]:
			wr.writerow(row)

			


if __name__ == "__main__": main()
