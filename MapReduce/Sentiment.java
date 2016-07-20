
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;
import java.util.*;
public class Sentiment {
public static void main(String[] args) throws Exception { 
	if (args.length != 2) {
System.err.println("Usage: Sentiment <keyword> <website>");
System.exit(-1); 
}
String keyword = args[0];
String website = args[1];
String database = "";
String outfile = keyword + "_" + website;
keyword = keyword.toLowerCase();
website = website.toLowerCase();
keyword = keyword.replace('_', ' ');
if (website.equals("reddit")) {
	database = "input_r";
}
else if (website.equals("yelp")) {
	database = "input_y";
}
else if (website.equals("twitter")) {
	database = "input_t";
}
else {
	System.out.println("Please enter one of the following for the second input: Reddit, Yelp, Twitter.");
	return;
}


Configuration conf = new Configuration(); 
conf.set("mapred.textoutputformat.separator", "\u0020");
conf.set("keyword", keyword);
conf.set("website", website);
Job job = new Job(conf, "sentiment"); 
job.setJarByClass(Sentiment.class); 
job.setJobName("Sentiment");
FileInputFormat.addInputPath(job, new Path(database)); 
FileOutputFormat.setOutputPath(job, new Path(outfile));
job.setMapperClass(SentimentMapper.class); 
job.setReducerClass(SentimentReducer.class);
job.setOutputKeyClass(Text.class); 
job.setOutputValueClass(Text.class);
System.exit(job.waitForCompletion(true) ? 0 : 1); }
}
