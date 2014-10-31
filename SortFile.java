import java.io.*;
import java.util.*;

class ShowData
{
	int id;
	double attr;

	public void setId(int id) 
	{
                this.id = id;
        }

        public int getId() 
	{
                return id;
        }

	public void setAttr(double attr)
	{
		this.attr = attr;
	}

	public double getAttr()
	{
		return attr;
	}
}

class SortFile 
{
	public static void quick_srt(ShowData array[],int low, int n)
	{	
		int lo = low;
		int hi = n;
		double mid = array[(lo + hi) / 2].attr;
		while (lo <= hi) 
		{
			while (array[lo].attr > mid) 
			{
				lo++;
			}
			while (array[hi].attr < mid)
			{
				hi--;
  			}
  			if (lo <= hi) 
			{
  				double T = array[lo].attr;
  				array[lo].attr = array[hi].attr;
  				array[hi].attr = T;
				int I = array[lo].id;
				array[lo].id = array[hi].id;
				array[hi].id = I;
				lo++;
				hi--;
  			}
  		}
		if (low < hi)
  			quick_srt(array, low, hi);
 		if(lo < n )
    			quick_srt(array, lo, n);
	}

        public static void SortFile(String filename, String dfname, int count) 
	{
                int j = 0;
                ShowData data[] = new ShowData[count];
                try 
		{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

                        while ((strLine = br.readLine()) != null) 
			{

				String splitarray[] = strLine.split("\t");
				String id = splitarray[0];
				String attribute = splitarray[1];
                                data[j] = new ShowData();
                                data[j].setId(Integer.parseInt(id));
                                data[j].setAttr(Double.parseDouble(attribute));
				j++;
			}

			//Implementing Quicksort**********************Sort by data[j]
			
			quick_srt(data,0,j-1);

                        File file = new File(dfname);
                        FileWriter fw = new FileWriter(file, true);
                        BufferedWriter at = new BufferedWriter(fw);
                        for (int i = 0; i < j; i++) 
			{
                                ShowData show = data[i];
                                int id = show.getId();
				double attrib = show.getAttr();
                                at.write(id + "\t" + attrib + "\n");
                        }
                        at.close();
			in.close();
                } catch (Exception e) 
		{
			System.out.println("exception caught");
                }
        }
}
