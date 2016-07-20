with open("tip_list.csv", "r") as tips, open("business_list.csv", "r") as busis, open("output.csv", "a") as result:
	bus_dict = {}
	tip_list = []
	for line1 in busis:
		bfields = line1.split('|')
		bus_dict[bfields[0]] = bfields[1]

	for line2 in tips:
		tfields = line2.split('|')
		tfields[0] = bus_dict[tfields[0]]
		tip_list.append([tfields[0], tfields[1]])

	for i in tip_list:
		tmp = i[0].rstrip('\n') + '|' + i[1]
		result.write(tmp)
		
tips.close()
busis.close()
result.close()