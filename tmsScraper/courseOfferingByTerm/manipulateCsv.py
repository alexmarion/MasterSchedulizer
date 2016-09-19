import csv
import string

# if1 = open('UGTerms_ACCT-ENVR.csv', "rb")
# if1 = open('UGTerms_FASH-LIT1.csv', "rb")
if1 = open('UGTerms_MATE-WRIT.csv', "rb")

csv_f1 = csv.reader(if1)
# csv_f2 = csv.reader(if2)
# csv_f3 = csv.reader(if3)

# csv1 = []
# csv2 = []
# csv3 = []

# for row in csv_f1:
# 	csv1.append(row)
# for row in csv_f2:
# 	csv2.append(row)
# for row in csv_f3:
# 	csv3.append(row)

# csv1.extend(csv2)
# csv1.extend(csv3)


of1  = open('termTakenList3.csv', "wb")
writer = csv.writer(of1)


exclude = set(string.punctuation)
termTaken = ""

lineNumber = 0

for row in csv_f1:
	if ''.join(row).strip():
		row[1] = ''.join(ch for ch in row[1] if ch not in exclude)
		if row[2]:
			if row[3] == 'X':
				termTaken += "1"
			else:
				termTaken += "0"
			if row[4] == 'X':
				termTaken += "1"
			else:
				termTaken += "0"
			if row[5] == 'X':
				termTaken += "1"
			else:
				termTaken += "0"
			if row[6] == 'X':
				termTaken += "1"
			else:
				termTaken += "0"
			print lineNumber, row, len(row), row[6]
			lineNumber += 1
		writer.writerow( (row[1], row[2], termTaken) )
		termTaken = ""
if1.close()
# if2.close()
# if3.close()

of1.close()