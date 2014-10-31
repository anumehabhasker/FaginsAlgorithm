import java.io.*;
import java.util.*;

class DataPreprocess extends SortFile
{
	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();

		int count = 0;
		String filename;
		Scanner input = new Scanner(System.in);

		System.out.println("Please enter the file name of the dataset to be processed:");
		filename = input.nextLine();
		try
		{
			System.out.println("\n\nCreating temporary files for Attributes 1, 2, 7, 8 & 9");
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			FileWriter a1 = new FileWriter("a1.txt");
			BufferedWriter at1 = new BufferedWriter(a1);
			FileWriter a2 = new FileWriter("a2.txt");
			BufferedWriter at2 = new BufferedWriter(a2);
			FileWriter a7 = new FileWriter("a7.txt");
			BufferedWriter at7 = new BufferedWriter(a7);
			FileWriter a8 = new FileWriter("a8.txt");
			BufferedWriter at8 = new BufferedWriter(a8);
			FileWriter a9 = new FileWriter("a9.txt");
			BufferedWriter at9 = new BufferedWriter(a9);

			FileWriter a10 = new FileWriter("original");
			BufferedWriter at10 = new BufferedWriter(a10);

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   
			{
				String splitarray[] = strLine.split("\t");
				at1.write(splitarray[0] + "\t" + splitarray[1] + "\n");
				at2.write(splitarray[0] + "\t" + splitarray[2] + "\n");
				at7.write(splitarray[0] + "\t" + splitarray[7] + "\n");
				at8.write(splitarray[0] + "\t" + splitarray[8] + "\n");
				at9.write(splitarray[0] + "\t" + splitarray[9] + "\n");
				at10.write(splitarray[0] + "\t" + splitarray[1] + "\t" + splitarray[2] + "\t" + splitarray[7] + "\t" + splitarray[8] + "\t" + splitarray[9] + "\n");
				count++;
			}
			//Close the input stream
			in.close();
			at1.close();
			at2.close();
			at7.close();
			at8.close();
			at9.close();
			at10.close();
			System.out.println("Temporary files created successfully...");

			System.out.println("Sorting the temporary file for Attribute 1 and storing in attr1.txt");
			String fname = "a1.txt";
			String dfname = "attr1";
			SortFile(fname,dfname,count);
			File f = new File(fname);
			f.delete();
			System.out.println("attr1 created successfully. temporary file deleted.");

			System.out.println("Sorting the temporary file for Attribute 2 and storing in attr2.txt");
			fname = "a2.txt";
			dfname = "attr2";
			SortFile(fname,dfname,count);
			f = new File(fname);
			f.delete();
			System.out.println("attr2 created successfully. temporary file deleted.");

			System.out.println("Sorting the temporary file for Attribute 7 and storing in attr7.txt");
			fname = "a7.txt";
			dfname = "attr7";
			SortFile(fname,dfname,count);
			f = new File(fname);
			f.delete();
			System.out.println("attr7 created successfully. temporary file deleted.");
	
			System.out.println("Sorting the temporary file for Attribute 8 and storing in attr8.txt");
			fname = "a8.txt";
			dfname = "attr8";
			SortFile(fname,dfname,count);
			f = new File(fname);
			f.delete();
			System.out.println("attr8 created successfully. temporary file deleted.");
	
			System.out.println("Sorting the temporary file for Attribute 9 and storing in attr9.txt");
			fname = "a9.txt";
			dfname = "attr9";
			SortFile(fname,dfname,count);
			f = new File(fname);
			f.delete();
			System.out.println("attr9 created successfully. temporary file deleted.");

			System.out.println("Merging all sorted attr* files in a new file - input.");
			FileInputStream f1 = new FileInputStream("attr1");
			DataInputStream in1 = new DataInputStream(f1);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			FileInputStream f2 = new FileInputStream("attr2");
			DataInputStream in2 = new DataInputStream(f2);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
			String strLine2;
	
			FileInputStream f7 = new FileInputStream("attr7");
			DataInputStream in7 = new DataInputStream(f7);
			BufferedReader br7 = new BufferedReader(new InputStreamReader(in7));
			String strLine7;
	
			FileInputStream f8 = new FileInputStream("attr8");
			DataInputStream in8 = new DataInputStream(f8);
			BufferedReader br8 = new BufferedReader(new InputStreamReader(in8));
			String strLine8;
	
			FileInputStream f9 = new FileInputStream("attr9");
			DataInputStream in9 = new DataInputStream(f9);
			BufferedReader br9 = new BufferedReader(new InputStreamReader(in9));
			String strLine9;

			FileWriter fw = new FileWriter("input");
			BufferedWriter bw = new BufferedWriter(fw);

			while ((strLine1 = br1.readLine()) != null && (strLine2 = br2.readLine()) != null && (strLine7 = br7.readLine()) != null && (strLine8 = br8.readLine()) != null && (strLine9 = br9.readLine()) != null)   
			{
				String splitarray1[] = strLine1.split("\t");
				String splitarray2[] = strLine2.split("\t");
				String splitarray7[] = strLine7.split("\t");
				String splitarray8[] = strLine8.split("\t");
				String splitarray9[] = strLine9.split("\t");
				bw.write(splitarray1[0] + "\t" + splitarray2[0] + "\t" + splitarray7[0] + "\t" + splitarray8[0] + "\t" + splitarray9[0] + "\n");
			}
			in1.close();
			in2.close();
			in7.close();
			in8.close();
			in9.close();
			bw.close();
			dfname = "attr1";
			f = new File(dfname);
			f.delete();
			dfname = "attr2";
			f = new File(dfname);
			f.delete();
			dfname = "attr7";
			f = new File(dfname);
			f.delete();
			dfname = "attr8";
			f = new File(dfname);
			f.delete();
			dfname = "attr9";
			f = new File(dfname);
			f.delete();
			System.out.println("All sorted attr* files merged in a new file - input successfully. All attr* files deleted.");

		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		fw = new FileWriter("output.txt",true);
		bw = new BufferedWriter(fw);

		bw.write("Working on file:- " + filename + "\nTotal time taken to pre-process the dataset -> " + totalTime + " milliseconds\n");
		bw.close();
		}
		catch (Exception e)
		{//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
