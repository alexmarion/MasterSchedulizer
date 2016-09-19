import requests
files = {'f': ('UGTerms_ACCT-ENVR.pdf', open('UGTerms_ACCT-ENVR.pdf', 'rb'))}
files1 = {'f': ('UGTerms_FASH-LIT.pdf', open('UGTerms_FASH-LIT.pdf', 'rb'))}
files2 = {'f': ('UGTerms_MATE-WRIT.pdf', open('UGTerms_MATE-WRIT.pdf', 'rb'))}
response = requests.post("https://pdftables.com/api?key=q8hp1om27gj5&format=xlsx-single", files=files)
response.raise_for_status() # ensure we notice bad responses
with open("UGTerms_MATE-WRIT.csv", "wb") as f:
    f.write(response.content)