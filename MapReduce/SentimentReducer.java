

import java.io.IOException;
import org.apache.hadoop.io.IntWritable; import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.*;
import java.util.*;
public class SentimentReducer
extends Reducer<Text, Text, Text, Text> {
@Override
public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	int count = 0;
	for (org.apache.hadoop.io.Text value : values) {
		count++;
		String s = key.toString();
		if ((s.equals("Neutral Attitude") || s.equals("Good Attitude") || 
				s.equals("Bad Attitude") || s.equals("Excellent Attitude")
				|| s.equals("Terrible Attitude")) && value.toString().length() > 50) {
			context.write(key, value);
		}
	}
	 context.write(key, new Text(String.valueOf(count))); 
  }
}
