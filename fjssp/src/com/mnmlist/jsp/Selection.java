package com.mnmlist.jsp;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Set;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class Selection
{

	/*
	 * @param caculate the P(fitness[i])of every Ⱦɫ��
	 * @allFitness[]ԭʼ��Ⱥ��ͨ�������������ĸ������Ӧ��
	 * @allProbabilities[]���е������屻ѡ�еĸ���
	 */
	public static void transformFitnessToDistribution(int[] allFitness,
			double[] allProbabilities)
	{
		int length = allFitness.length;
		int sum = 0;
		int min = allFitness[0];
		int minIndex = 0;
		int i = 0;
		// find the minimum value and index
		for (; i < length; i++)
		{
			sum += allFitness[i];
			if (allFitness[i] < min)
			{
				min = allFitness[i];
				minIndex = i;
			}
		}
		// find the number of save minimum value
		//int[] flag = new int[length];
		BitSet flag=new BitSet();
		int sameCount = 0;
		for (i = 0; i < length; i++)
		{
			if (allFitness[i] == min)
			{
				flag.set(i);
				sameCount++;
			}
		}
		double sumDis = 0.0;
		for (i = 0; i < length; i++)
		{
			if (flag.get(i))
			{
				allProbabilities[i] = -1.0;
			} else
			{
				allProbabilities[i] = 1.0 / (allFitness[i] - min);
				sumDis += allProbabilities[i];
			}
		}
		double[] disClone = new double[length];
		System.arraycopy(allProbabilities, 0, disClone, 0, length);
		Arrays.sort(disClone);
		double v = 2 * disClone[length - 1] - disClone[length - 2];
		sumDis += v * sameCount;
		for (i = 0; i < length; i++)
		{
			if (flag.get(i))
			{
				allProbabilities[i] = v;
			}
			allProbabilities[i] = allProbabilities[i] / sumDis;
		}
	}
	/*
	 * @Allprobabilities[]���е������屻ѡ�еĸ���
	 * @initPopuCount ��ʼ��Ⱥ�Ĵ�С
	 * @isSelection[]��ʼ��Ⱥ�;��������������ĸ����Ƿ�ѡ�еı�ʶ����
	 */
	public static void selection(double[] allprobabilities,
			int initPopulationCount, double start, int[] perSelectionCount)
	{
		
		int length = allprobabilities.length;
		double gap = 1.0 / initPopulationCount;
		if (start < gap / (length + 1))
			start = gap / (length + 1);
		double sum = start;
		while (sum < 1.0)
			sum += gap;
		double zeroStart = sum - 1.0;
		if (zeroStart < gap / length)
			zeroStart = gap / length;
		double last = 0.0;
		int i = 0;
		for (; i < length; i++)
		{
			zeroStart -= last;
			double sub = 0;
			if (allprobabilities[i] > gap)
			{
				perSelectionCount[i] = (int) (allprobabilities[i] / gap);
				sub = perSelectionCount[i] * gap;
				zeroStart += sub;
			}
			if (allprobabilities[i] > zeroStart)
			{
				perSelectionCount[i]++;
				zeroStart += gap;
			}
			last = allprobabilities[i];
		}
	}

}
