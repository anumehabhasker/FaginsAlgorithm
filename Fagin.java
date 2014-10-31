import java.io.*;
import java.util.*;
//class to hold the objects, their attribute sums & count of attributes seen. Also functions to get or set these parameters.
class ObjectSeen
{
	long key;
	double attr_sum;
	int attr_count;
	ObjectSeen()
	{
		this.attr_sum = 0.0;
		this.attr_count = 0;
	}
	public void setKey(long k) 
	{
       	        this.key = k;
       	}
       	public long getKey() 
	{
       	        return key;
       	}

	public void addSum(double v)
	{
		this.attr_sum = this.attr_sum + v;
	}
	public void setSum(double v)
	{
		this.attr_sum = v;
	}

	public double getSum()
	{
		return attr_sum;
	}

	public void setCount() 
	{
       	        this.attr_count++;
       	}
       	public int getCount() 
	{
       	        return attr_count;
       	}
}

public class Fagin
{
//quicksort to sort the ObjectSeen type array based on key.
	public static void quick_srt_keys(ObjectSeen array[],int low,int n)
	{	
		int lo = low;
		int hi = n;
		long mid = array[(lo + hi) / 2].getKey();
		while (lo <= hi) 
		{
			while (array[lo].getKey() < mid) 
			{
				lo++;
			}
			while (array[hi].getKey() > mid)
			{
				hi--;
  			}
  			if (lo <= hi) 
			{
  				long T = array[lo].getKey();
  				array[lo].setKey(array[hi].getKey());
  				array[hi].setKey(T);
				lo++;
				hi--;
  			}
  		}
		if (low < hi)
  			quick_srt_keys(array, low, hi);
 		if(lo < n )
    			quick_srt_keys(array, lo, n);
	}

//quicksort to sort the ObjectSeen type array based on sum of attr1, attr2, attr7, attr8 & attr9.
	public static void quick_srt_values(ObjectSeen array[],int low,int n)
	{	
		int lo = low;
		int hi = n;
		double mid = array[(lo + hi) / 2].getSum();
		while (lo <= hi) 
		{
			while (array[lo].getSum() > mid) 
			{
				lo++;
			}
			while (array[hi].getSum() < mid)
			{
				hi--;
  			}
  			if (lo <= hi) 
			{
  				long T = array[lo].getKey();
  				array[lo].setKey(array[hi].getKey());
  				array[hi].setKey(T);
				double V = array[lo].getSum();
  				array[lo].setSum(array[hi].getSum());
  				array[hi].setSum(V);
				lo++;
				hi--;
  			}
  		}
		if (low < hi)
  			quick_srt_values(array, low, hi);
 		if(lo < n )
    			quick_srt_values(array, lo, n);
	}


  	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();	//note the start time of the program.

		String filename = "input",strLine;
		FileInputStream fstream;
		DataInputStream input;
		BufferedReader br;

		ObjectSeen obj[] = new ObjectSeen[100000];

		int i, j, count = 0; //counter for the ObjectSeen obj[]
		int found = 0; // flag to indicate that object matched
		int n, read = 1,k=0;
		int seen_k = 0,top_k;

		Scanner in = new Scanner(System.in);

		System.out.println("\n\nPlease enter the value for k in top_k:");
		top_k = in.nextInt();

		try
		{
		fstream = new FileInputStream(filename);
		input = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(input));

//reading the file "input" line by line
		System.out.println("\nProcessing the input file now..." + "\n");

		while ((strLine = br.readLine()) != null && read == 1)
		{
			String Splitline[] = strLine.split("\t");
//After spliting the strLine on tab, Splitline holds 5 keys.
			for(n=0; n<5; n++)
			{//running a loop on the 5 keys in the Splitline
				if(top_k > seen_k)
				{//if we have seen less than top_k key's get more keys.
					for(i=0; i<count; i++)
					{//compare the key in Splitline[n] to all the key's already in the obj[] array. If any match, just increament its attr_count value. If this attr_count value has become 5, it means for this key, all the 5 attributes have been seen and increament seen_k by 1. 
						if(obj[i].getCount() != 5 && obj[i].getKey() == Long.parseLong(Splitline[n]))
						{
							obj[i].setCount();
							if(obj[i].getCount() == 5)
							{
								seen_k++;
							}
							found = 1;
							break ;
						}
					}//if comparison failes, insert the key in the array obj[].
					if(found == 0)
					{
						obj[count] = new ObjectSeen();
						obj[count].setKey(Long.parseLong(Splitline[n]));
						obj[count].setCount();
						count = count+1;
					}
					else
						found = 0;
				}
				else
					read = 0; //if we have seen all the 5 attributes of atleast top_k key's, terminate the while loop
			}
		}
		br.close(); //close the file
		System.out.println("Processing complete..." + "\n" + "Calling sort" + "\n");

		quick_srt_keys(obj,0,count-1); // sort the obj[] array on key's

		fstream = new FileInputStream("original");
		input = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(input));
		j = 0;
//get the attributes of the key's in the obj[] array from the temporary file. This temporary file holds all the keys of the original dataset and just the attr1, attr2, attr7, attr8, attr9.
		while ((strLine = br.readLine()) != null)
		{
			j++;
			for (i = k;i<count;i++)
			{
				if(obj[i].getKey() == j)
				{	
					k++;
					String SplitLine[] = strLine.split("\t");
					for(n=1; n<6; n++)
					{
						obj[i].addSum(Double.parseDouble(SplitLine[n]));
					}
					break;	
				}
				else if(obj[i].getKey() > j)
					break;
			}
		}
		br.close();
		quick_srt_values(obj,0,count-1);	//sort the obj[] on the sum of the attributes

//check the end time
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;

//write output to the file
		System.out.println("Writing the top_k to file output.txt in your local drive");
		FileWriter fw = new FileWriter("output.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Top_k = " + top_k +"\n");
		bw.write("Top_k key-values are:\n");
		for(i=0; i<top_k; i++)
		{
			bw.write(obj[i].getKey() + "\t" + obj[i].getSum() + "\n");
		}
		bw.write("Total Key-value pairs processed -> " + count + "\n");
		bw.write("Total time taken to process -> " + totalTime + " milliseconds\n\n\n");
		bw.close();
		System.out.println("Writing complete");

		}
		catch (Exception e)
		{//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}



	}
}

import java.io.*;
import java.util.*;
//class to hold the objects, their attribute sums & count of attributes seen. Also functions to get or set these parameters.
class ObjectSeen
{
	long key;
	double attr_sum;
	int attr_count;
	ObjectSeen()
	{
		this.attr_sum = 0.0;
		this.attr_count = 0;
	}
	public void setKey(long k) 
	{
       	        this.key = k;
       	}
       	public long getKey() 
	{
       	        return key;
       	}

	public void addSum(double v)
	{
		this.attr_sum = this.attr_sum + v;
	}
	public void setSum(double v)
	{
		this.attr_sum = v;
	}

	public double getSum()
	{
		return attr_sum;
	}

	public void setCount() 
	{
       	        this.attr_count++;
       	}
       	public int getCount() 
	{
       	        return attr_count;
       	}
}

public class Fagin
{
//quicksort to sort the ObjectSeen type array based on key.
	public static void quick_srt_keys(ObjectSeen array[],int low,int n)
	{	
		int lo = low;
		int hi = n;
		long mid = array[(lo + hi) / 2].getKey();
		while (lo <= hi) 
		{
			while (array[lo].getKey() < mid) 
			{
				lo++;
			}
			while (array[hi].getKey() > mid)
			{
				hi--;
  			}
  			if (lo <= hi) 
			{
  				long T = array[lo].getKey();
  				array[lo].setKey(array[hi].getKey());
  				array[hi].setKey(T);
				lo++;
				hi--;
  			}
  		}
		if (low < hi)
  			quick_srt_keys(array, low, hi);
 		if(lo < n )
    			quick_srt_keys(array, lo, n);
	}

//quicksort to sort the ObjectSeen type array based on sum of attr1, attr2, attr7, attr8 & attr9.
	public static void quick_srt_values(ObjectSeen array[],int low,int n)
	{	
		int lo = low;
		int hi = n;
		double mid = array[(lo + hi) / 2].getSum();
		while (lo <= hi) 
		{
			while (array[lo].getSum() > mid) 
			{
				lo++;
			}
			while (array[hi].getSum() < mid)
			{
				hi--;
  			}
  			if (lo <= hi) 
			{
  				long T = array[lo].getKey();
  				array[lo].setKey(array[hi].getKey());
  				array[hi].setKey(T);
				double V = array[lo].getSum();
  				array[lo].setSum(array[hi].getSum());
  				array[hi].setSum(V);
				lo++;
				hi--;
  			}
  		}
		if (low < hi)
  			quick_srt_values(array, low, hi);
 		if(lo < n )
    			quick_srt_values(array, lo, n);
	}


  	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();	//note the start time of the program.

		String filename = "input",strLine;
		FileInputStream fstream;
		DataInputStream input;
		BufferedReader br;

		ObjectSeen obj[] = new ObjectSeen[100000];

		int i, j, count = 0; //counter for the ObjectSeen obj[]
		int found = 0; // flag to indicate that object matched
		int n, read = 1,k=0;
		int seen_k = 0,top_k;

		Scanner in = new Scanner(System.in);

		System.out.println("\n\nPlease enter the value for k in top_k:");
		top_k = in.nextInt();

		try
		{
		fstream = new FileInputStream(filename);
		input = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(input));

//reading the file "input" line by line
		System.out.println("\nProcessing the input file now..." + "\n");

		while ((strLine = br.readLine()) != null && read == 1)
		{
			String Splitline[] = strLine.split("\t");
//After spliting the strLine on tab, Splitline holds 5 keys.
			for(n=0; n<5; n++)
			{//running a loop on the 5 keys in the Splitline
				if(top_k > seen_k)
				{//if we have seen less than top_k key's get more keys.
					for(i=0; i<count; i++)
					{//compare the key in Splitline[n] to all the key's already in the obj[] array. If any match, just increament its attr_count value. If this attr_count value has become 5, it means for this key, all the 5 attributes have been seen and increament seen_k by 1. 
						if(obj[i].getCount() != 5 && obj[i].getKey() == Long.parseLong(Splitline[n]))
						{
							obj[i].setCount();
							if(obj[i].getCount() == 5)
							{
								seen_k++;
							}
							found = 1;
							break ;
						}
					}//if comparison failes, insert the key in the array obj[].
					if(found == 0)
					{
						obj[count] = new ObjectSeen();
						obj[count].setKey(Long.parseLong(Splitline[n]));
						obj[count].setCount();
						count = count+1;
					}
					else
						found = 0;
				}
				else
					read = 0; //if we have seen all the 5 attributes of atleast top_k key's, terminate the while loop
			}
		}
		br.close(); //close the file
		System.out.println("Processing complete..." + "\n" + "Calling sort" + "\n");

		quick_srt_keys(obj,0,count-1); // sort the obj[] array on key's

		fstream = new FileInputStream("original");
		input = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(input));
		j = 0;
//get the attributes of the key's in the obj[] array from the temporary file. This temporary file holds all the keys of the original dataset and just the attr1, attr2, attr7, attr8, attr9.
		while ((strLine = br.readLine()) != null)
		{
			j++;
			for (i = k;i<count;i++)
			{
				if(obj[i].getKey() == j)
				{	
					k++;
					String SplitLine[] = strLine.split("\t");
					for(n=1; n<6; n++)
					{
						obj[i].addSum(Double.parseDouble(SplitLine[n]));
					}
					break;	
				}
				else if(obj[i].getKey() > j)
					break;
			}
		}
		br.close();
		quick_srt_values(obj,0,count-1);	//sort the obj[] on the sum of the attributes

//check the end time
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;

//write output to the file
		System.out.println("Writing the top_k to file output.txt in your local drive");
		FileWriter fw = new FileWriter("output.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Top_k = " + top_k +"\n");
		bw.write("Top_k key-values are:\n");
		for(i=0; i<top_k; i++)
		{
			bw.write(obj[i].getKey() + "\t" + obj[i].getSum() + "\n");
		}
		bw.write("Total Key-value pairs processed -> " + count + "\n");
		bw.write("Total time taken to process -> " + totalTime + " milliseconds\n\n\n");
		bw.close();
		System.out.println("Writing complete");

		}
		catch (Exception e)
		{//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}



	}
}

