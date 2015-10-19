package com.mnmlist.fjssp.logic;

import java.util.BitSet;
import java.util.Random;

import com.mnmlist.fjssp.data.ProblemInfo;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0 use for generate the
 */
public class GeneOperator
{
	public static void main(String args[])
	{
		// int dna1[]={1, 3, 2, 3, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2, 1, 1, 1,
		// 3, 2, 3, 1, 1, 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 3, 1, 2, 2, 1, 1,
		// 2, 1, 1, 2, 1, 2, 1, 2, 1, 3, 1, 1, 3, 6, 5, 8, 4, 3, 0, 1, 8, 8, 5,
		// 3, 3, 5, 2, 8, 2, 5, 0, 3, 7, 8, 8, 0, 3, 1, 5, 6, 9, 2, 4, 2, 1, 2,
		// 5, 0, 7, 4, 0, 0, 6, 7, 9, 6, 7, 4, 1, 9, 1, 4, 4, 6, 9, 7, 9, 9};
		// int dna[]={1, 3, 2, 1, 1, 3, 1, 1, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1,
		// 3, 2, 3, 1, 1, 1, 2, 3, 2, 1, 2, 1, 1, 2, 1, 2, 2, 3, 1, 1, 2, 1, 2,
		// 2, 1, 2, 1, 1, 3, 2, 2, 2, 3, 1, 2, 2, 9, 5, 9, 8, 1, 7, 1, 3, 8, 5,
		// 6, 4, 5, 3, 7, 7, 2, 1, 1, 0, 5, 1, 5, 8, 6, 7, 3, 2, 6, 9, 9, 2, 3,
		// 4, 2, 5, 0, 6, 7, 0, 6, 4, 8, 9, 0, 8, 2, 4, 3, 0, 4, 0, 8, 9, 4};
		// //dna[41]=1;
		// File file=new File("mk01.txt");
		// //get the problem description,such as
		// populationCount,crossoverRate,mutationRate
		// ProblemInfo input=InitProblemDescription.getProblemDesFromFile(file);
		// int maxOperationCount[]=input.getMachineCountArr();
		//
		// // CaculateFitness.evaluate(dna, input);
		// // int len=dna.length/2;
		// // for(int i=0;i<len;i++)
		// // {
		// // machineSeqMutation(dna,input,i);
		// // }
		// machineSeqMutation(dna, input, 41);
		// for(int i=0;i<55;i++)
		// {
		// if(maxOperationCount[i]<dna[i])
		// System.out.println("Error.");
		// System.out.println("maxCount:"+maxOperationCount[i]+","+(i+1)+"th:"+dna[i]);
		// }
		Random random = new Random();
		for (int i = 0; i < 20; i++)
		{
			System.out.println(random.nextDouble());
		}
	}

	/**
	 * flexible job shop crossover method,include machine sequence and operation
	 * sequence crossover
	 * 
	 * @param dna1
	 *            one feasible solution
	 * @param dna2
	 *            another feasilbe solution
	 * @param ProblemInfo
	 *            descrip operation to totalIndex,the operation and the
	 *            machineNo and time info .etc.
	 * @return two feasible solutions(DNA sequence) after crosser over
	 */
	public static int[][] fjsspCrossover(int dna1[], int dna2[],
			int operDnaSeq1[], int operDnaSeq2[], ProblemInfo ProblemInfo)
	{
		int newDnaArr[][] = new int[2][dna1.length];
		int newDna1[] = newDnaArr[0];
		int newDna2[] = newDnaArr[1];
		int operSequenceLen = dna1.length / 2;
		System.arraycopy(dna1, 0, newDna1, 0, operSequenceLen);
		System.arraycopy(dna2, 0, newDna2, 0, operSequenceLen);
		Random rand = new Random();
		machineSeqCrossover(newDna1, newDna2, rand);
		int jobCount = ProblemInfo.getJobCount();
		System.arraycopy(dna1, operSequenceLen, operDnaSeq1, 0, operSequenceLen);
		System.arraycopy(dna2, operSequenceLen, operDnaSeq2, 0, operSequenceLen);
		int newDnaOperSeq1[] = operSeqCrossover(operDnaSeq1, operDnaSeq2,
				jobCount, rand);
		int newDnaOperSeq2[] = operSeqCrossover(operDnaSeq2, operDnaSeq1,
				jobCount, rand);
		System.arraycopy(newDnaOperSeq1, 0, newDna1, operSequenceLen,
				operSequenceLen);
		System.arraycopy(newDnaOperSeq2, 0, newDna2, operSequenceLen,
				operSequenceLen);
		return newDnaArr;
	}

	/**
	 * @param dna1
	 *            one feasible solution
	 * @param dna2
	 *            another feasilbe solution
	 * @param jobCount
	 *            the total number of the task
	 * @param rand
	 *            create random number
	 * @return the operation sequence after cross over
	 */
	public static int[] operSeqCrossover(int operDnaSeq1[], int operDnaSeq2[],
			int jobCount, Random rand)
	{
		int len = operDnaSeq1.length;
		int dnaCopy[] = new int[len];
		System.arraycopy(operDnaSeq1, 0, dnaCopy, 0, len);
		int newDna[] = new int[len];
		// 生成随机的交叉起点和终点
		int start = rand.nextInt(len);
		int end = rand.nextInt(len);
		int temp = 0;
		if (start > end)
		{
			temp = start;
			start = end;
			end = temp;
		}
		// 标记
		int i = 0;
		int[] gongxuCount = new int[jobCount];
		int[] flagCount = new int[end - start + 1];
		for (i = 0; i < start; i++)
			++gongxuCount[dnaCopy[i]];
		for (i = start; i <= end; i++)
		{
			flagCount[i - start] = ++gongxuCount[dnaCopy[i]];
		}
		// 从parent2生成新串
		int newLen = end + 1 + len;
		int indexNna2 = 0;
		int indexNewDna = 0;
		for (i = end + 1; i < newLen; i++)
		{

			if (i >= len)
				indexNna2 = i % len;
			else
				indexNna2 = i;
			newDna[indexNewDna++] = operDnaSeq2[indexNna2];
		}

		// 标记新串中要保存的旧串的值
		newLen = end - start + 1;
		int appearIndex = 0;
		int gongjianNo = 0;
		int count = 0;
		BitSet set = new BitSet();
		int j = 0;
		for (i = 0; i < newLen; i++)
		{
			gongjianNo = dnaCopy[start + i];
			appearIndex = flagCount[i];// pay attention to it
			count = 0;
			for (j = 0; j < len; j++)
			{
				if (newDna[j] == gongjianNo)
					count++;
				if (count == appearIndex)
				{
					set.set(j);
					break;
				}
			}
		}
		// 赋新值
		newLen = end + 1 + len - (end - start + 1);
		indexNewDna = 0;
		int indexOldDna = 0;
		for (i = end + 1; i < newLen; i++)
		{

			while (indexNewDna < len && set.get(indexNewDna))
				indexNewDna++;
			if (indexNewDna == len)
				break;
			if (i >= len)
				indexOldDna = i % len;
			else
				indexOldDna = i;
			dnaCopy[indexOldDna] = newDna[indexNewDna++];

		}
		return dnaCopy;
	}

	/**
	 * two point crossover
	 * 
	 * @param dna1
	 *            one feasible solution
	 * @param dna2
	 *            another feasilbe solution
	 * @param rand
	 *            create random number
	 */
	public static void machineSeqCrossover(int dna1[], int dna2[], Random rand)
	{
		// System.out.println(Arrays.toString(dna1));
		int seqLen = dna1.length / 2;
		int randomIndex1 = rand.nextInt(seqLen);
		int randomIndex2 = rand.nextInt(seqLen);
		int temp = 0;
		if (randomIndex1 > randomIndex2)
		{
			temp = randomIndex1;
			randomIndex1 = randomIndex2;
			randomIndex2 = temp;
		}
		for (int i = randomIndex1; i <= randomIndex2; i++)
		{
			temp = dna1[i];
			dna1[i] = dna2[i];
			dna2[i] = temp;
		}
	}

	public static void fjsspMutation(ProblemInfo input, int[] dna)
	{
		// the mutation of the operation sequence
		int len = dna.length / 2;
		Random random = new Random();
		int posa = random.nextInt(len);
		int posb = random.nextInt(len);
		while (posa == posb)
			posb = random.nextInt(len);
		int temp = 0;
		if (posa > posb)
		{
			temp = posa;
			posa = posb;
			posb = temp;
		}
		operSeqMutation(dna, posa + len, posb + len);
		// the mutation of the machine sequence
		posa = random.nextInt(len);
		machineSeqMutation(dna, input, posa);
	}

	/**
	 * @param dna
	 *            one feasible solution
	 * @param posa
	 *            one mutation index
	 * @param posb
	 *            another mutation index
	 */
	public static void operSeqMutation(int[] dna, int posa, int posb)
	{
		int temp = dna[posa];
		dna[posa] = dna[posb];
		dna[posb] = temp;
	}

	/**
	 * @param dna
	 *            one feasible solution
	 * @param input
	 *            descrip the operation and the machineNo and time info .etc.
	 * @param position
	 *            the mutation index of the machine sequence
	 */
	public static void machineSeqMutation(int[] dna, ProblemInfo input,
			int position)
	{
		int machineNoTimeArr[] = input.getProDesMatrix()[position];
		int index = 0;
		while (machineNoTimeArr[index] == 0)
			index++;
		int min = machineNoTimeArr[index];
		for (int i = index + 1; i < machineNoTimeArr.length; i++)
		{
			if (machineNoTimeArr[i] != 0 && machineNoTimeArr[i] < min)
			{
				min = machineNoTimeArr[i];
			}
		}
		int count = 0;
		for (int i = index; i < machineNoTimeArr.length; i++)
		{
			if (machineNoTimeArr[i] != 0)
			{
				count++;
				if (min == machineNoTimeArr[i])
					break;
			}
		}
		dna[position] = count;
	}
}
