import json
from pprint import pprint

def byteify(input):
    if isinstance(input, dict):
        return {byteify(key):byteify(value) for key,value in input.iteritems()}
    elif isinstance(input, list):
        return [byteify(element) for element in input]
    elif isinstance(input, unicode):
        return input.encode('utf-8')
    else:
        return input

with open("/home/stud/Downloads/yelp_academic_dataset_tip.json") as bsr:
	with open("tip_list.csv", "a") as bsw:
		for liner in bsr:
			data = json.loads(liner)
			data = byteify(data)
			data['text'] = data['text'].encode('string-escape')
			linew = data['business_id'] + '|' + data['text'] + '\n'
			bsw.write(linew)

bsr.close()
bsw.close()