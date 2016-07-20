from pyspark import SparkContext

def parse(line):
	if(line[-4:] == "2015"):
		line = line[:-42]
	if(line.find(":") > 0):
		line = line[line.find(":") + 2:]
	return line

sc = SparkContext('local', 'pyspark')
text_file = sc.textFile("tweets.txt")
text_file = text_file.filter(lambda line: len(line) > 10)
text_file = text_file.map(parse)
text_file = text_file.filter(lambda line: len(line) > 5)
text_file.saveAsTextFile("input_t")
