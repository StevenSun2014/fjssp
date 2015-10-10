package com.mnmlist.fjssp.logic;

import java.util.Random;

import com.mnmlist.fjssp.data.Entry;
import com.mnmlist.fjssp.data.ProblemInputII;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class GenerateDNA
{

	public static void fjsspGenerateOneDNA(ProblemInputII problemInputII, int[] dna,
			Entry[] entries)
	{
		int jobCount=problemInputII.getJobCount();
		int len=dna.length/2;
		// entries 工种名及其对应的工序数
		int i = 0;
		int tempjobCount = jobCount;
		Random generator = problemInputII.getRandom();
		int randomRange = 32767;
		int ran = 0;
		for (i = 0; i < len; i++)
		{
			ran = generator.nextInt(randomRange) % tempjobCount;// containerSize：jobcount;
			dna[i+len] = entries[ran].index;// 每次产生一个基因
			entries[ran].value--;// 工种名及其对应的工序数减一，即剩余的工序数目减一
			if (entries[ran].value == 0)
			{
				// 工序数目为0时，说明该工种的所有工序已经排完
				tempjobCount--;// 工种数目
				entries[ran].index = entries[tempjobCount].index;// 删除工序数目为0的那个工种
				entries[ran].value = entries[tempjobCount].value;// 通过将最后一个工种及其对应的工序数目赋予工序数目为0的变量来实现
			}
		}
	}

	/**
	 * @param jobCount
	 * @param dna
	 *            the DNA sequence
	 * @param entries
	 *            include the jobNo and the procedureNo
	 * @param dnaLength
	 */
	public static void generateOneDNA(int jobCount, int[] dna, Entry[] entries,
			int dnaLength)
	{

		// entries 工种名及其对应的工序数
		int i = 0;
		int tempjobCount = jobCount;
		Random generator = new Random();
		int randomRange = 32767;
		int ran = 0;
		for (i = 0; i < dnaLength; i++)
		{
			ran = generator.nextInt(randomRange) % tempjobCount;// containerSize：jobcount;
			dna[i] = entries[ran].index;// 每次产生一个基因
			entries[ran].value--;// 工种名及其对应的工序数减一，即剩余的工序数目减一
			if (entries[ran].value == 0)
			{
				// 工序数目为0时，说明该工种的所有工序已经排完
				tempjobCount--;// 工种数目
				entries[ran].index = entries[tempjobCount].index;// 删除工序数目为0的那个工种
				entries[ran].value = entries[tempjobCount].value;// 通过将最后一个工种及其对应的工序数目赋予工序数目为0的变量来实现
			}
		}
	}

	/**
	 * @param jobCount
	 * @param populationSize
	 * @param dnaArray
	 *            the DNA sequence of the whole population
	 * @param entries
	 *            include the jobNo and the procedureNo
	 * @param dnaLength
	 */
	public static void generateDNAs(int jobCount, int populationSize,
			int[][] dnaArray, Entry[] entries, int dnaLength)
	{
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
			generateOneDNA(jobCount, dnaArray[i], tempEntries, dnaLength);
		}
	}
}
