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
	 * // use for mutation
	 * @param dna the DNA sequence
	 * @param length the DNA length
	 * @param posa a procedure
	 * @param posb a procedure
	 */
	public static void mutation(int[] dna, int length, int posa, int posb) {
		int temp = dna[posa];
		dna[posa] = dna[posb];
		dna[posb] = temp;
	}

	
	/**
	 * // use for crosserover
	 * @param dna1 the father DNA sequence
	 * @param dna2 the father DNA sequence
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
