package com.mnmlist.fjssp.logic;

import java.util.Random;

import com.mnmlist.fjssp.data.BestSolution;
import com.mnmlist.fjssp.data.Entry;
import com.mnmlist.fjssp.data.ProblemInputII;
import com.mnmlist.fjssp.lib.UtilLib;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */

class GA
{
	int dnaLen = 0;
	public BestSolution solve( ProblemInputII input)
	{
		Random generator = new Random();
		// start time
		long startTime = System.currentTimeMillis();
		int k = 0;
		int jobCount=input.getJobCount();
		Entry[] entries = new Entry[jobCount];// 工种数目
		for (int q = 0; q < jobCount; q++)
		{
			entries[q] = new Entry();
		}
		int totalOperationCount = input.getMaxOperationCount();// 总工序数
		int[][] operationToIndex= input.getOperationToIndex();
		int i = 0;
		for (; i < jobCount; i++)
		{
			entries[i].index=i;
			int operationCount = 0;
			int start=operationToIndex[i][0];
			int end=0;
			if(i+1<jobCount)
				end=operationToIndex[i+1][0]-1;
			else {
				end=totalOperationCount-1;
			}
			operationCount=end-start+1;
			entries[i].value=operationCount;
			//totalOperationCount += operationCount;
		}
		int dnaLength = totalOperationCount*2;// the length of DNA is equal to the
		int poputlationCount=input.getPopulationCount();
		int[][] dnaMatrix = new int[poputlationCount][dnaLength];
		// generate all the DNAs
		GenerateDNA.generateDNAs(jobCount, poputlationCount,
				dnaMatrix, entries, dnaLength);
		int[] fitness = new int[poputlationCount];
		int minFitness = 0;
		i = 0;
		fitness[0] = CaculateFitness.evaluate(dnaMatrix[i], input);
		minFitness = fitness[0];
		int minIndex = 0;
		for (i = 1; i < poputlationCount; i++)
		{
			fitness[i] = CaculateFitness.evaluate(dnaMatrix[i],input);
			if (fitness[i] < minFitness)
			{
				minFitness = fitness[i];
				minIndex = i;
			}
		}
		int bestChromosome[] = new int[dnaLength];
		System.arraycopy(dnaMatrix[minIndex], 0, bestChromosome, 0, dnaLength);
		int count = 0;
		int randomRange = 32767;
		int fatherIndex = 0;
		int motherIndex = 0;
		int index = 0;
		int posa = 0;
		int posb = 0;
		int min = 0;
		double crossoverRate=input.getCrossoverRate();
		while (!UtilLib.isEnd(input,count, (long) startTime))
		{
			// 基因重组
			int crossCount = (int) (poputlationCount * crossoverRate);
			if ((crossCount & 1) == 1)
				crossCount--;
			int[][] newDNAs = new int[crossCount][dnaLength];
			for (i = 0; i < crossCount; i += 2)
			{
				fatherIndex = generator.nextInt(randomRange)
						% (poputlationCount);
				motherIndex = generator.nextInt(randomRange)
						% (poputlationCount);
				int matrix[][]=GeneOperator.fjsspCrossover(dnaMatrix[fatherIndex], dnaMatrix[motherIndex], input);
				newDNAs[i] =matrix[0];
				newDNAs[i + 1] = matrix[1];
			}
			// 基因突变
			double mutationRate=input.getMutationRate();
			double randomNumber=0;
			//for()
//			int mutationCount = (int) (crossCount *mutationRate);
//			for (i = 0; i < mutationCount; i++)
//			{
//				index = generator.nextInt(randomRange) % crossCount;
//				posa = generator.nextInt(randomRange) % dnaLength;
//				posb = generator.nextInt(randomRange) % dnaLength;
//				min = posa < posb ? posa : posb;
//				GeneOperator.mutation(newDNAs[index], min, posa
//						+ posb - min);
//			}
			int[] newFitness = new int[crossCount];
			for (i = 0; i < crossCount; i++)
				newFitness[i] = CaculateFitness.evaluate(newDNAs[i],
						input);
			int allLength = poputlationCount + crossCount;
			int[] allFitness = new int[allLength];
			System.arraycopy(fitness, 0, allFitness, 0,poputlationCount);
			System.arraycopy(newFitness, 0, allFitness,poputlationCount, crossCount);
			double[] allProbabilities = new double[allLength];
			Selection.transformFitnessToDistribution(allFitness,
					allProbabilities);
			double start = (generator.nextInt(randomRange) % 100000) / 100000;
			int[] perSelectCount = new int[allLength];
			Selection.selection(allProbabilities, poputlationCount,start, perSelectCount);
			int[][] nextGen = new int[poputlationCount][dnaLength];
			index = 0;
			int j = 0;
			for (i = 0; i < poputlationCount; i++)
			{
				for (j = 0; j < perSelectCount[i]; j++)
				{
					System.arraycopy(dnaMatrix[i], 0, nextGen[index], 0,
							dnaLength);
					index++;
				}
			}
			for (i = 0; i < crossCount; i++)
			{
				for (j = 0; j < perSelectCount[i + poputlationCount]; j++)
				{
					System.arraycopy(newDNAs[i], 0, nextGen[index], 0,
							dnaLength);
					index++;
				}
			}
			for (int p = 0; p <poputlationCount; p++)
				System.arraycopy(nextGen[p], 0, dnaMatrix[p], 0, dnaLength);
			int latestMinFitness = CaculateFitness.evaluate(dnaMatrix[0],input);
			int latestIndexMin = 0;
			for (i = 1; i <poputlationCount; i++)
			{
				fitness[i] = CaculateFitness.evaluate(dnaMatrix[i],input);
				if (fitness[i] < latestMinFitness)
				{
					latestMinFitness = fitness[i];
					latestIndexMin = i;
				}
			}
			if (latestMinFitness < minFitness)
			{
				System.arraycopy(dnaMatrix[latestIndexMin], 0, bestChromosome,
						0, dnaLength);
				minFitness = latestMinFitness;
				minIndex = latestIndexMin;
			}
			count++;
		}
		BestSolution bestSolution = new BestSolution(bestChromosome, minFitness);
		return bestSolution;
	}
}// end of class GA

