package com.mnmlist.fjssp.logic;

import java.util.Arrays;
import java.util.Random;

import com.mnmlist.fjssp.data.BestSolution;
import com.mnmlist.fjssp.data.Entry;
import com.mnmlist.fjssp.data.Operation;
import com.mnmlist.fjssp.data.ProblemInfo;
import com.mnmlist.fjssp.lib.UtilLib;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0 the whole logic of the problem scheduling process
 */
public class FlexibleJobShop
{
	/**
	 * the whole logic of the flexible job shop sheduling problem
	 * 
	 * @param input
	 *            the problem description which has been arranged
	 * @return the best solution for this iteration
	 */
	public BestSolution solve(ProblemInfo input, Operation[][] operationMatrix)
	{
		int jobCount = input.getJobCount();
		int totalOperCount = input.getTotalOperationCount();
		int populationCount = input.getPopulationCount();
		double crossoverRate = input.getCrossoverRate();
		double mutationRate = input.getMutationRate();
		int dnaLength = totalOperCount * 2;// the length of DNA
		int minIndex = 0;
		int minFitness = 0;
		int randomIndex = 0;
		int i = 0, j = 0;
		Random generator = new Random();
		long startTime = System.currentTimeMillis();// start time
		// the jobNo and the operation count of given jobNo
		Entry[] entries = new Entry[jobCount];
		for (int q = 0; q < jobCount; q++)
		{
			entries[q] = new Entry();
		}
		// jobNo and operNo to index
		int[][] operationToIndex = input.getOperationToIndex();
		int OperIndexStart = 0, OperIndexEnd = 0;
		for (i=0; i < jobCount; i++)
		{
			entries[i].index = i;
			OperIndexStart = operationToIndex[i][0];
			if (i + 1 < jobCount)
				OperIndexEnd = operationToIndex[i + 1][0] - 1;
			else
				OperIndexEnd = totalOperCount - 1;
			entries[i].value = OperIndexEnd - OperIndexStart + 1;
		}
		int[][] dnaMatrix = new int[populationCount][];
//		 int gsCount=(int)(populationCount*input.getGsRate());
//		 int lsCount=(int)(populationCount*input.getLsRate())+gsCount;
//		 //create machine sequence
//		 for(i=0;i<gsCount;i++)
//		 dnaMatrix[i]=InitProblemDescription.globalSearch(input);
//		 for(i=gsCount;i<lsCount;i++)
//		 dnaMatrix[i]=InitProblemDescription.localSearch(input);
//		 for(i=lsCount;i<populationCount;i++)
//		 dnaMatrix[i]=InitProblemDescription.randomSearch(input);
		//create machine sequence
		for (i = 0; i < populationCount; i++)
			dnaMatrix[i] = InitProblemDescription.globalSearch(input);
		// create operation sequence
		GenerateDNA.fjsspGenerateDNAs(input, dnaMatrix, entries);
		// gene operator
		int crossCount = (int) (populationCount * crossoverRate);
		if ((crossCount & 1) == 1)
			crossCount--;
		int allLength=populationCount+crossCount;
		int mutationCount = (int) (mutationRate * populationCount);
		int[] allFitness = new int[allLength];
		allFitness[0] = CaculateFitness.evaluate(dnaMatrix[0], input,
				operationMatrix);
		minFitness = allFitness[0];
		for (i = 1; i < populationCount; i++)
		{
			allFitness[i] = CaculateFitness.evaluate(dnaMatrix[i], input,
					operationMatrix);
			if (allFitness[i] < minFitness)
			{
				minFitness = allFitness[i];
				minIndex = i;
			}
		}
		int bestChromosome[] = new int[dnaLength];
		System.arraycopy(dnaMatrix[minIndex], 0, bestChromosome, 0, dnaLength);
		int count = 0;
		int fatherIndex = 0;
		int motherIndex = 0;
		int index = 0;
		int operDnaSeq1[] = new int[totalOperCount];//for cross over
		int operDnaSeq2[] = new int[totalOperCount];
		double[] allProbabilities = new double[allLength];
		int[] perSelectCount = new int[allLength];
		int[][] newDnaMatrix = new int[allLength][dnaLength];
		while (!UtilLib.isEnd(input, count, startTime))
		{
			// copy parent generation to new generation
			System.arraycopy(bestChromosome, 0, newDnaMatrix[0], 0, dnaLength);
			for (i = 1; i < populationCount; i++)
				System.arraycopy(dnaMatrix[i], 0, newDnaMatrix[i], 0, dnaLength);
			// gene Crossover,copy new born individual to new generation
			for (i = 0; i < crossCount; i += 2)
			{
				fatherIndex = generator.nextInt(populationCount);
				motherIndex = generator.nextInt(populationCount);
				while (fatherIndex == motherIndex)
					motherIndex = generator.nextInt(populationCount);
				int matrix[][] = GeneOperator.fjsspCrossover(
						dnaMatrix[fatherIndex], dnaMatrix[motherIndex],
						operDnaSeq1, operDnaSeq2, input);
				System.arraycopy(matrix[0], 0,
						newDnaMatrix[populationCount + i], 0, dnaLength);
				System.arraycopy(matrix[1], 0,
						newDnaMatrix[populationCount + i + 1], 0,dnaLength);
			}
			//caculate fitness and select the best individual
			int latestMinFitness = CaculateFitness.evaluate(newDnaMatrix[0],input, operationMatrix);
			int latestMinIndex = 0;
			allFitness[0]=latestMinFitness;
			for (i = 1; i < allLength; i++)
			{
				allFitness[i] = CaculateFitness.evaluate(
						newDnaMatrix[i], input,operationMatrix);
				if (allFitness[i] < latestMinFitness)
				{
					latestMinFitness = allFitness[i];
					latestMinIndex = i;
				}
			}
			if (latestMinFitness < minFitness)
			{
				System.arraycopy(newDnaMatrix[latestMinIndex], 0, bestChromosome,
						0, dnaLength);
				minFitness = latestMinFitness;
				minIndex = latestMinIndex;
			}
			//select individuals of the next generation
			Arrays.fill(allProbabilities, 0);
			Selection.transformFitnessToDistribution(allFitness,allProbabilities);
			double start = generator.nextDouble();
			Arrays.fill(perSelectCount, 0);
			Selection.selection(allProbabilities, populationCount, start,perSelectCount);
			index = 0;
			for(i=0;i<allLength;i++)
			{
				for (j = 0; j < perSelectCount[i]; j++)
				{
					System.arraycopy(newDnaMatrix[i], 0, dnaMatrix[index], 0,dnaLength);
					index++;
				}
			}
			// gene mutation
			for (i = 0; i < mutationCount; i++)
			{
				randomIndex = generator.nextInt(populationCount);
				GeneOperator.fjsspMutation(input, dnaMatrix[randomIndex]);
			}
			//judge whether the loop count has exceeded.
			count++;
		}
		BestSolution bestSolution = new BestSolution(bestChromosome, minFitness);
		return bestSolution;
	}
}// end of class GA

