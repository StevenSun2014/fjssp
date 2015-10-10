package com.mnmlist.jsp;

import java.util.BitSet;
import java.util.Random;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class GeneOperator {
	/**
	 * flexible job shop crossover method,include machine sequence and operation sequence crossover
	 * @param dna1 one feasible solution
	 * @param dna2 another feasilbe solution
	 * @param problemInputII  descrip operation to totalIndex,the operation and the machineNo and time info .etc.
	 * @return two feasible solutions(DNA sequence) after crosser over
	 */
	public static int[][]fjsspCrossover(int dna1[],int dna2[],ProblemInputII problemInputII)
	{
		int newDnaArr[][]=new int[2][dna1.length];
		int newDna1[]=newDnaArr[0];
		int newDna2[]=newDnaArr[1];
		System.arraycopy(dna1, 0, newDna1, 0, dna1.length);
		System.arraycopy(dna2, 0, newDna2, 0, dna1.length);
		Random rand=problemInputII.getRandom();
		machineSeqCrossover(newDna1, newDna2, rand);
		int jobCount=problemInputII.getJobCount();
		int operationSeq1[]=operSeqCrossover(dna1, dna2, jobCount, rand);
		int operationSeq2[]=operSeqCrossover(dna2, dna1, jobCount, rand);
		int operSeqLen=dna1.length/2;
		System.arraycopy(operationSeq1, 0, newDna1, operSeqLen, operSeqLen);
		System.arraycopy(operationSeq2, 0, newDna2, operSeqLen, operSeqLen);
		return newDnaArr;
	}
	/**
	 * two point crossover
	 * @param dna1 one feasible solution
	 * @param dna2 another feasilbe solution
	 * @param rand create random number
	 */
	public static void machineSeqCrossover(int dna1[], int dna2[], Random rand) {
		int seqLen=dna1.length/2;
		int randomIndex1=rand.nextInt(seqLen);
		int randomIndex2=rand.nextInt(seqLen);
		int temp=0;
		if(randomIndex1>randomIndex2)
		{
			temp=randomIndex1;
			randomIndex1=randomIndex2;
			randomIndex2=temp;
		}
		for(int i=randomIndex1;i<=randomIndex2;i++)
		{
			temp=dna1[i];
			dna1[i]=dna2[i];
			dna2[i]=temp;
		}
	}
	/**
	 * @param dna1 one feasible solution
	 * @param dna2 another feasilbe solution
	 * @param jobCount the total number of the task
	 * @param rand create random number
	 * @return the operation sequence after cross over
	 */
	public static int[] operSeqCrossover(int dna1[], int dna2[],
			int jobCount, Random rand) {
		int len = dna1.length/2;
		int dnaCopy[] = new int[len];
		System.arraycopy(dna1, len, dnaCopy, 0, len);
		int newDna[] = new int[len];
		// 生成随机的交叉起点和终点
		int start = rand.nextInt(len)+len;
		int end = rand.nextInt(len)+len;
		int temp = 0;
		if (start > end) {
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
		for (i = start; i <= end; i++) {
			flagCount[i - start] = ++gongxuCount[dnaCopy[i]];
		}
		// 从parent2生成新串
		int newLen = end + 1 + len;
		int indexNna2 = 0;
		int indexNewDna = 0;
		for (i = end + 1; i < newLen; i++) {

			if (i >= len)
				indexNna2 = i % len;
			else
				indexNna2 = i;
			newDna[indexNewDna++] = dna2[indexNna2];
		}

		// 标记新串中要保存的旧串的值
		newLen = end - start + 1;
		int appearIndex = 0;
		int gongjianNo = 0;
		int count = 0;
		BitSet set = new BitSet();
		int j = 0;
		for (i = 0; i < newLen; i++) {
			gongjianNo = dnaCopy[start + i];
			appearIndex = flagCount[i];// i pay attention to it
			count = 0;
			for (j = 0; j < len; j++) {
				if (newDna[j] == gongjianNo)
					count++;
				if (count == appearIndex) {
					set.set(j);
					break;
				}
			}
		}
		// 赋新值
		newLen = end + 1 + len - (end - start + 1);
		indexNewDna = 0;
		int indexOldDna = 0;
		for (i = end + 1; i < newLen; i++) {

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
	 * @param dna one feasible solution
	 * @param posa one mutation index
	 * @param posb another mutation index
	 */
	public static void operSeqMutation(int[] dna, int posa, int posb) {
		int temp = dna[posa];
		dna[posa] = dna[posb];
		dna[posb] = temp;
	}
	/**
	 * @param dna one feasible solution
	 * @param proDesMatrix descrip the operation and the machineNo and time info .etc.
	 * @param position the mutation index of the machine sequence 
	 */
	public static void machineSeqMutation(int[] dna, ProblemInputII problemInputII,int position) {
		int machineNoTimeArr[]=problemInputII.getProDesMatrix()[position];
		int index=0;
		while(machineNoTimeArr[index]==0)index++;
		int min=machineNoTimeArr[index];
		for(int i=index+1;i<machineNoTimeArr.length;i++)
		{
			if(machineNoTimeArr[i]!=0)
			{
				if(min>machineNoTimeArr[i])
					min=machineNoTimeArr[i];
			}
		}
		int count=0;
		for(int i=0;i<machineNoTimeArr.length;i++)
		{
			if(machineNoTimeArr[i]!=0)
			{
				count++;
				if(min==machineNoTimeArr[i])
					break;
			}
		}
		dna[position]=count;
	}
	/**
	 * // use for mutation
	 * @param dna the DNA sequence
	 * @param length the DNA length
	 * @param posa a procedure
	 * @param posb a procedure
	 */
	public static void mutation(int[] dna, int posa, int posb) {
		int temp = dna[posa];
		dna[posa] = dna[posb];
		dna[posb] = temp;
	}
	/**
	 * // use for crosserover
	 * @param dna1 the father DNA sequence
	 * @param dna2 the mother DNA sequence
	 * @param input the time and order information of the problem 
	 * @param rand a random object
	 * @return the new DNA sequence
	 */
	public static int[] OXCrosserover(int dna1[], int dna2[],
			ProblemInput input, Random rand) {

		int len = dna1.length;
		int dnaCopy[] = new int[len];
		System.arraycopy(dna1, 0, dnaCopy, 0, len);
		int newDna[] = new int[len];
		// 生成随机的交叉起点和终点
		int start = rand.nextInt(len);
		int end = rand.nextInt(len);
		int temp = 0;
		if (start > end) {
			temp = start;
			start = end;
			end = temp;
		}
		// 标记
		int i = 0;
		int[] gongxuCount = new int[input.jobCount];
		int[] flagCount = new int[end - start + 1];
		for (i = 0; i < start; i++)
			++gongxuCount[dnaCopy[i]];
		for (i = start; i <= end; i++) {
			flagCount[i - start] = ++gongxuCount[dnaCopy[i]];
		}
		// 从parent2生成新串
		int newLen = end + 1 + len;
		int indexNna2 = 0;
		int indexNewDna = 0;
		for (i = end + 1; i < newLen; i++) {

			if (i >= len)
				indexNna2 = i % len;
			else
				indexNna2 = i;
			newDna[indexNewDna++] = dna2[indexNna2];
		}

		// 标记新串中要保存的旧串的值
		newLen = end - start + 1;
		int appearIndex = 0;
		int gongjianNo = 0;
		int count = 0;
		BitSet set = new BitSet();
		int j = 0;
		for (i = 0; i < newLen; i++) {
			gongjianNo = dnaCopy[start + i];
			appearIndex = flagCount[i];// i pay attention to it
			count = 0;
			for (j = 0; j < len; j++) {
				if (newDna[j] == gongjianNo)
					count++;
				if (count == appearIndex) {
					set.set(j);
					break;
				}
			}
		}
		// 赋新值
		newLen = end + 1 + len - (end - start + 1);
		indexNewDna = 0;
		int indexOldDna = 0;
		for (i = end + 1; i < newLen; i++) {

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
}
