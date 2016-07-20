import java.io.*;
import java.util.*;
public class PostProcess {
//javac PostProcess.java
//java PostProcess ./folder website
  public static class Pair {
	  String s;
	  int count;
	  public Pair(String s, int count) {
		  this.s = s;
		  this.count = count;
	  }
  }
  public static class Comp implements Comparator<Pair> {
	  public int compare(Pair one, Pair two) {
		  if (one.count < two.count) {
			  return -1;
		  }
		  else if (one.count > two.count) {
			  return 1;
		  }
		  else {
			  return 0;
		  }
	  }
  }
  public static void main(String[] args) {
	  if (args.length != 2) {
		  System.err.println("Usage: PostProcess <in> <website>");
		  System.exit(-1); 
		  }
	  PriorityQueue<Pair> heap = new PriorityQueue<Pair>(100, new Comp());
	  int[] count = new int[5];
	  List<List<String>> list = new ArrayList<List<String>>();
	  for (int i = 0; i < 5; i++) {
		  list.add(new ArrayList<String>());
	  }
	  File folder = new File(args[0]);
	  File[] listOfFiles = folder.listFiles();
	  String website;
	  int total;
	  if (args[1].toLowerCase().equals("reddit")) {
		  website = "Reddit";
		  total = 53851542;
	  }
	  else if (args[1].toLowerCase().equals("yelp")) {
		  website = "Yelp";
		  total = 490405;
	  }
	  else {
		  website = "Twitter";
		  total = 846324;
	  }
	  for (int i = 0; i < listOfFiles.length; i++) {
	    File file = listOfFiles[i];
	    FileReader fileReader;
	    try {
	    	fileReader = new FileReader(file);
	    	BufferedReader bufferedReader = new BufferedReader(fileReader);
	        String line;
	         while((line = bufferedReader.readLine()) != null) {
	        	 if (line.startsWith("Excellent Attitude")) {
	            	 String result = parser(line.substring(18, line.length()));
	            	 if (result.charAt(0) >= '0' && result.charAt(0) <= '9') {
	            		 count[0] = Integer.parseInt(result);
	            	 }
	            	 else {
	            		 list.get(0).add(result);
	            	 }
	             }
	             else if (line.startsWith("Good Attitude")) {
	            	 String result = parser(line.substring(13, line.length()));
	            	 if (result.charAt(0) >= '0' && result.charAt(0) <= '9') {
	            		 count[1] = Integer.parseInt(result);
	            	 }
	            	 else {
	            		 list.get(1).add(result);
	            	 }
	             }
	             else if (line.startsWith("Neutral Attitude")) {
	            	 String result = parser(line.substring(16, line.length()));
	            	 if (result.charAt(0) >= '0' && result.charAt(0) <= '9') {
	            		 count[2] = Integer.parseInt(result);
	            	 }
	            	 else {
	            		 list.get(2).add(result);
	            	 }
	             }
	             else if (line.startsWith("Bad Attitude")) {
	            	 String result = parser(line.substring(12, line.length()));
	            	 if (result.charAt(0) >= '0' && result.charAt(0) <= '9') {
	            		 count[3] = Integer.parseInt(result);
	            	 }
	            	 else {
	            		 list.get(3).add(result);
	            	 } 
	             }
	             else if (line.startsWith("Terrible Attitude")) {
	            	 String result = parser(line.substring(17, line.length()));
	            	 if (result.charAt(0) >= '0' && result.charAt(0) <= '9') {
	            		 count[4] = Integer.parseInt(result);
	            	 }
	            	 else {
	            		 list.get(4).add(result);
	            	 }
	             }
	             else {
	            	 String word = "";
	            	 int number = 0;
	            	for (int pos = 0; pos < line.length(); pos++) {
	            		if (line.charAt(pos) == ' ') {
	            			word = line.substring(0, pos);
	            			number = Integer.parseInt(parser(line.substring(pos, line.length())));
	            		}
	            	}
	            	heap.add(new Pair(word, number));
	            	if (heap.size() >= 21) {
	            		heap.poll();
	            	}
	             }
	         }
	         bufferedReader.close();
	    } catch (Exception e) {
	    	System.out.println(i + "File does not exist!");
	    }   
	  }
	 int sum = 0;
	 for (int i = 0; i < 5; i++) {
		 sum += count[i];
	 }
	 System.out.print(sum + " out of " + total + " (");
	 System.out.format("%.4f", (double)sum / total * 100);
	 System.out.println("%) comments on " + website + " contain this keyword.");
      double[] percent = new double[5];
      for (int i = 0; i < 5; i++) {
         if (sum != 0) {
	       percent[i] = (double)count[i] / sum * 100;
		}
	    else {
		  percent[i] = 0;
          }
}  
	 System.out.println();
	 System.out.format("%.1f", percent[0]);    
	 System.out.println("% of them seem to like it quite a lot, such as:");
	 for (int i = 0; i < Math.min(5, list.get(0).size()); i++) {
		 System.out.println("  " + list.get(0).get(i));
	 }
	 System.out.println();
System.out.format("%.1f", percent[1]);  
	 System.out.println("% of them think it's good, such as:");
	 for (int i = 0; i < Math.min(5, list.get(1).size()); i++) {
		 System.out.println("  " + list.get(1).get(i));
	 }
	 System.out.println();
      System.out.format("%.1f", percent[2]);
	 System.out.println("% of them neither like it nor hate it, such as:");
	 for (int i = 0; i < Math.min(5, list.get(2).size()); i++) {
		 System.out.println("  " + list.get(2).get(i));
	 }
	 System.out.println();
      System.out.format("%.1f", percent[3]);
	 System.out.println("% of them are not a fan of it, such as:");
	 for (int i = 0; i < Math.min(5, list.get(3).size()); i++) {
		 System.out.println("  " + list.get(3).get(i));
	 }
	 System.out.println();
      System.out.format("%.1f", percent[4]);
	 System.out.println("% of them really hate it, such as:");
	 for (int i = 0; i < Math.min(5, list.get(4).size()); i++) {
		 System.out.println("  " + list.get(4).get(i));
	 }
	 System.out.println();
	 System.out.println("This is the visualized distribution of people's opinions:");
	 System.out.println();
	 int average = (sum <= 200)? sum / 20 : sum / 200;
	 for (int i = 0; i < 5; i++) {
		 if (i == 0) {
			 System.out.print("Excellent ");
		 }
		 else if (i == 1) {
			 System.out.print("Good      ");
		 }
		 else if (i == 2) {
			 System.out.print("Neutral   ");
		 }
		 else if (i == 3) {
			 System.out.print("Bad       ");
		 }
		 else if (i == 4) {
			 System.out.print("Terrible  ");
		 }
		 int number = (average <= 0)? count[i] : count[i] / average;
		 System.out.print("|");
		 for (int j = 0; j < number; j++) {
			 System.out.print('-');
		 }
		 for (int offset = number; offset < 80; offset++) {
			 System.out.print(' ');
		 }
		 System.out.format("%.1f", percent[i]);
		 System.out.println("%");
	 }
	 List<Pair> pairs = new ArrayList<Pair>();
	 while (!heap.isEmpty()) {
		 pairs.add(heap.poll());
	 }
	 System.out.println();
	 System.out.println("Last but not least, here are the top 20 frequent words appearing together with the keyword:");
	 System.out.println();
	 for (int i = pairs.size() - 1; i >= 0; i--) {
		 if (pairs.get(i).s.equals("fuck")) {
			 System.out.print("f**k");
		 }
		 else {
			 System.out.print(pairs.get(i).s);
		 }
	   for (int offset = pairs.get(i).s.length(); offset < 40; offset++) {
		 System.out.print(" ");
	   }
	   System.out.println(pairs.get(i).count + " times");
	 }
  }
  
  public static String parser(String s) {
	  int n = s.length();
	  for (int i = 0; i < n; ) {
		  if (s.charAt(i) == ' ') {
			  i++;
		  }
		  else {
			  return s.substring(i, n);
		  }
	  }
	  return " ";
  }
}
