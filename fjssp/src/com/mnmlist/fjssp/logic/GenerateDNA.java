package com.mnmlist.fjssp.logic;

import java.util.Random;

import com.mnmlist.fjssp.data.Entry;
import com.mnmlist.fjssp.data.ProblemInfo;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class GenerateDNA
{

	public static void fjsspGenerateOneDNA(ProblemInfo input, int[] dna,Entry[] entries)
	{
		int jobCount=input.getJobCount();
		int len=dna.length/2;
		// entries are jobNo and operation count coressponded
		int i = 0;
		int tempjobCount = jobCount;
		Random generator = input.getRandom();
		int ran = 0;
		for (i = 0; i < len; i++)
		{
			ran = generator.nextInt(tempjobCount);// containerSize：jobcount remained
			dna[i+len] = entries[ran].index;// generate one gene at a time
			entries[ran].value--;// the operation count of some jobNo minus one
			if (entries[ran].value == 0)
			{
				// when operation count equals 0,that's to say the job has been finished
				tempjobCount--;// job count
				// delete that job whose operation count equals 0
				entries[ran].index = entries[tempjobCount].index;
				//which could be done by passs the last value to the ran index one whose operation count equals 0
				entries[ran].value = entries[tempjobCount].value;
			}
		}
	}


	/**
	 * generate all the DNA of populationSize size
	 * @param dnaArray
	 *            the DNA sequence of the whole population
	 * @param entries
	 *            include the jobNo and the procedureNo
	 */
	public static void fjsspGenerateDNAs(ProblemInfo input,int[][] dnaArray, Entry[] entries)
	{
		int jobCount=input.getJobCount();
		int populationSize=input.getPopulationCount();
		// entries 工种名及其对应的工序数
		int i = 0;
		Entry[] tempEntries = new Entry[jobCount];
		for (int q = 0; q < jobCount; q++)
		{
			tempEntries[q] = new Entry();
		}
		for (; i < populationSize; i++)
		{
			for (int p = 0; p < jobCount; p++)
			{
				tempEntries[p].index = entries[p].index;
				tempEntries[p].value = entries[p].value;
			}
			fjsspGenerateOneDNA(input, dnaArray[i],tempEntries);
		}
	}
}
