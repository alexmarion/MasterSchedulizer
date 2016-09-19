#!/usr/bin/python
import os
import csv

def main():
	TMSMasterPath = "TMS/MASTERLIST"
	for root, dirs, files in os.walk(TMSMasterPath):
		for fname in files:
			fpath =  TMSMasterPath + "/" + fname
			print fpath
			with open(fpath, 'rb') as subjectFile:
				subjectReader = csv.reader(subjectFile)
				subjectRows = []
				for row in subjectReader:
					rowElems = row[3].split()
					if len(rowElems) > 1:
						row[3] = rowElems[0]
					subjectRows.append(row)

			with open(fpath, 'w') as subjectFile:
				subjectWriter = csv.writer(subjectFile, dialect='excel')
				for row in subjectRows:
						subjectWriter.writerow(row)


if __name__ == "__main__": main()
