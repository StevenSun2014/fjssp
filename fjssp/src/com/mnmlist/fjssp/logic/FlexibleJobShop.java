package com.mnmlist.fjssp.logic;

import java.util.Random;

import com.mnmlist.fjssp.data.BestSolution;
import com.mnmlist.fjssp.data.Entry;
import com.mnmlist.fjssp.data.ProblemInfo;
import com.mnmlist.fjssp.lib.UtilLib;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 * the whole logic of the problem scheduling process
 */
public class FlexibleJobShop
{
	/**
	 * the whole logic of the flexible job shop sheduling problem
	 * @param input
	 * 			the problem description which has been arranged
	 * @return
	 * 			the best solution for this iteration
	 */
	public BestSolution solve( ProblemInfo input)
	{
		Random generator = new Random();
		long startTime = System.currentTimeMillis();// start time
		int jobCount=input.getJobCount();
		Entry[] entries = new Entry[jobCount];// the jobNo and the operation count of given jobNo
		for (int q = 0; q < jobCount; q++)
		{
			entries[q] = new Entry();
		}
		int totalOperationCount = input.getTotalOperationCount();
		int[][] operationToIndex= input.getOperationToIndex();//jobNo and operNo to index
		int OperIndexStart=0,OperIndexEnd=0,operationCount=0,i=0;
		for (; i < jobCount; i++)
		{
			entries[i].index=i;
			operationCount = 0;
			OperIndexStart=operationToIndex[i][0];
			if(i+1<jobCount)
				OperIndexEnd=operationToIndex[i+1][0]-1;
			else {
				OperIndexEnd=totalOperationCount-1;
			}
			operationCount=OperIndexEnd-OperIndexStart+1;
			entries[i].value=operationCount;
			//totalOperationCount += operationCount;
		}
		int dnaLength = totalOperationCount*2;// the length of DNA is equal to the
		int populationCount=input.getPopulationCount();
		int[][] dnaMatrix = new int[populationCount][];
//		int gsCount=(int)(populationCount*input.getGsRate());
//		int lsCount=(int)(populationCount*input.getLsRate())+gsCount;
		//create machine sequence
//		for(i=0;i<gsCount;i++)
//			dnaMatrix[i]=InitProblemDescription.globalSearch(input);
//		for(i=gsCount;i<lsCount;i++)
//			dnaMatrix[i]=InitProblemDescription.localSearch(input);
//		for(i=lsCount;i<populationCount;i++)
//			dnaMatrix[i]=InitProblemDescription.randomSearch(input);
		for(i=0;i<populationCount;i++)
			dnaMatrix[i]=InitProblemDescription.globalSearch(input);
		// create operation sequence
		GenerateDNA.fjsspGenerateDNAs(input,dnaMatrix, entries);
		int[] fitness = new int[populationCount];
		int minFitness = 0;
		i = 0;
		fitness[0] = CaculateFitness.evaluate(dnaMatrix[i], input);
		//System.out.println("DNA length:"+dnaMatrix[0].length);
		minFitness = fitness[0];
		int minIndex = 0;
		for (i = 1; i < populationCount; i++)
		{
			fitness[i] = CaculateFitness.evaluate(dnaMatrix[i],input);
			//System.out.println(Arrays.toString(dnaMatrix[i]));
			if (fitness[i] < minFitness)
			{
				minFitness = fitness[i];
				minIndex = i;
			}
		}
		int bestChromosome[] = new int[dnaLength];
		System.arraycopy(dnaMatrix[minIndex], 0, bestChromosome, 0, dnaLength);
		int count = 0;
		int fatherIndex = 0;
		int motherIndex = 0;
		int index = 0;
		int operDnaSeq1[]=new int[totalOperationCount];
		int operDnaSeq2[]=new int[totalOperationCount];
		while (!UtilLib.isEnd(input,count, startTime))
		{
			double crossoverRate=input.getCrossoverRate();
			// gene crossover
			int crossCount = (int) (populationCount * crossoverRate);
			if ((crossCount & 1) == 1)
				crossCount--;
			int[][] newDNAs = new int[crossCount][dnaLength];
			for (i = 0; i < crossCount; i += 2)
			{
				fatherIndex = generator.nextInt(populationCount);
				motherIndex = generator.nextInt(populationCount);
				while(fatherIndex==motherIndex)
					motherIndex = generator.nextInt(populationCount);
				int matrix[][]=GeneOperator.fjsspCrossover(dnaMatrix[fatherIndex], 
						dnaMatrix[motherIndex],operDnaSeq1,operDnaSeq2,input);
				newDNAs[i] =matrix[0];
				newDNAs[i + 1] = matrix[1];
			}
			// gene mutation
			double mutationRate=input.getMutationRate();
			int randomIndex=0;
			int mutationCount=(int)(mutationRate*populationCount);
			for(i=0;i<mutationCount;i++)
			{
				randomIndex=generator.nextInt(populationCount);
				GeneOperator.fjsspMutation(input, dnaMatrix[randomIndex]);
			}
			int[] newFitness = new int[crossCount];
			for (i = 0; i < crossCount; i++)
				newFitness[i] = CaculateFitness.evaluate(newDNAs[i],input);
			int allLength = populationCount + crossCount;
			int[] allFitness = new int[allLength];
			System.arraycopy(fitness, 0, allFitness, 0,populationCount);
			System.arraycopy(newFitness, 0, allFitness,populationCount, crossCount);
			double[] allProbabilities = new double[allLength];
			Selection.transformFitnessToDistribution(allFitness,allProbabilities);
			double start = generator.nextDouble();
			int[] perSelectCount = new int[allLength];
			Selection.selection(allProbabilities, populationCount,start, perSelectCount);
			int[][] nextGen = new int[populationCount][dnaLength];
			index = 0;
			int j = 0;
			for (i = 0; i < populationCount; i++)
			{
				for (j = 0; j < perSelectCount[i]; j++)
				{
					System.arraycopy(dnaMatrix[i], 0, nextGen[index], 0,dnaLength);
					index++;
				}
			}
			for (i = 0; i < crossCount; i++)
			{
				for (j = 0; j < perSelectCount[i + populationCount]; j++)
				{
					System.arraycopy(newDNAs[i], 0, nextGen[index], 0,dnaLength);
					index++;
				}
				
			}
			for (int p = 0; p <populationCount; p++)
				System.arraycopy(nextGen[p], 0, dnaMatrix[p], 0, dnaLength);
			int latestMinFitness = CaculateFitness.evaluate(dnaMatrix[0],input);
			int latestIndexMin = 0;
			for (i = 1; i <populationCount; i++)
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
		BestSolution bestSolution=new BestSolution(bestChromosome,minFitness);
		return bestSolution;
	}
}// end of class GA

