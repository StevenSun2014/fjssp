package com.mnmlist.jsp;

import java.util.Random;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
class ProblemInput
{
	int machineNoMatrix[][];// 机器号矩阵
	int timeMatrix[][]; // 时间矩阵
	int machineCount; // 机器数
	int operationCount; // 操作数
	int jobCount; // 作业数目

	public ProblemInput(int gongzhongNum, int gongxuNum)
	{
		machineNoMatrix = new int[gongzhongNum][gongxuNum];
		timeMatrix = new int[gongzhongNum][gongxuNum];
		this.jobCount = gongzhongNum;
		this.operationCount = gongxuNum;
	}
}

class ParameterInput
{
	int initialPopulationCount;
	double crossoverRate;
	double mutationRate;
	long timeLimit;
	int iterationLimit;
	int crossStart;
	int crossEnd;
}

class Operation
{
	int jobNo;
	int operationNo;
	int startTime;
	int endTime;

	Operation()
	{
		jobNo = -1;
		operationNo = -1;
		startTime = -1;
		endTime = -1;
	}
}

class BestSolution
{
	private int[] bestChromesome;
	private int minTime;

	public BestSolution(int[] bestChromesome, int minTime)
	{
		this.bestChromesome = bestChromesome;
		this.minTime = minTime;
	}

	public int[] getBestChromesome()
	{
		return bestChromesome;
	}

	public int getBestFitness()
	{
		return minTime;
	}
}

// 查找某个工种对应的机器数目
class entry
{
	int index;// 工种号 jobNo
	int value;// 工序数 procedureNo

	entry()
	{
		index = -1;
		value = -1;
	}
}
/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version 1.0
 */
class GA
{
	int dnaLen = 0;
	public BestSolution solve(ParameterInput para, ProblemInput input)
	{
		Random generator = new Random();
		// start time
		long startTime = System.currentTimeMillis();
		int k = 0;
		entry[] entries = new entry[input.jobCount];// 工种数目
		for (int q = 0; q < input.jobCount; q++)
		{
			entries[q] = new entry();
		}
		int totalOperationCount = 0;// 总工序数
		int i = 0;
		for (; i < input.jobCount; i++)
		{
			entries[i].index = i;
			int operationCount = 0;
			for (k = 0; k < input.operationCount; k++)
			{//
				if (input.machineNoMatrix[i][k] != -1)
					operationCount++;
			}
			entries[i].value = operationCount;
			totalOperationCount += operationCount;
		}
		int dnaLength = totalOperationCount;// the length of DNA is equal to the
		// totalOperationCount
		this.dnaLen = totalOperationCount;
		para.crossStart = generator.nextInt(this.dnaLen - 1);
		int num = generator.nextInt(this.dnaLen);
		while (para.crossStart + num > this.dnaLen - 1)
			num = generator.nextInt(this.dnaLen);
		para.crossEnd = para.crossStart + num;
		int[][] dnaMatrix = new int[para.initialPopulationCount][dnaLength];
		// generate all the DNAs
		GenerateDNA.generateDNAs(input.jobCount, para.initialPopulationCount,
				dnaMatrix, entries, dnaLength);
		int[] fitness = new int[para.initialPopulationCount];
		int minFitness = 0;
		i = 0;
		fitness[0] = CaculateFitness.evaluate(dnaMatrix[i], dnaLength, input);
		minFitness = fitness[0];
		int minIndex = 0;
		for (i = 1; i < para.initialPopulationCount; i++)
		{
			fitness[i] = CaculateFitness.evaluate(dnaMatrix[i], dnaLength,
					input);
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
		while (!UtilLib.isEnd(para, count, (long) startTime))
		{
			// 基因重组
			int crossCount = (int) (para.initialPopulationCount * para.crossoverRate);
			if ((crossCount & 1) == 1)
				crossCount--;
			int[][] newDNAs = new int[crossCount][dnaLength];
			for (i = 0; i < crossCount; i += 2)
			{
				fatherIndex = generator.nextInt(randomRange)
						% (para.initialPopulationCount);
				motherIndex = generator.nextInt(randomRange)
						% (para.initialPopulationCount);
				newDNAs[i] = GeneOperator.OXCrosserover(dnaMatrix[fatherIndex],
						dnaMatrix[motherIndex], input, generator);
				newDNAs[i + 1] = GeneOperator.OXCrosserover(
						dnaMatrix[motherIndex], dnaMatrix[fatherIndex], input,
						generator);
			}
			// 基因突变
			int mutationCount = (int) (crossCount * para.mutationRate);
			for (i = 0; i < mutationCount; i++)
			{
				index = generator.nextInt(randomRange) % crossCount;
				posa = generator.nextInt(randomRange) % dnaLength;
				posb = generator.nextInt(randomRange) % dnaLength;
				min = posa < posb ? posa : posb;
				GeneOperator.mutation(newDNAs[index], dnaLength, min, posa
						+ posb - min);
			}
			int[] newFitness = new int[crossCount];
			for (i = 0; i < crossCount; i++)
				newFitness[i] = CaculateFitness.evaluate(newDNAs[i], dnaLength,
						input);
			int allLength = para.initialPopulationCount + crossCount;
			int[] allFitness = new int[allLength];
			System.arraycopy(fitness, 0, allFitness, 0,
					para.initialPopulationCount);
			System.arraycopy(newFitness, 0, allFitness,
					para.initialPopulationCount, crossCount);
			double[] allProbabilities = new double[allLength];
			Selection.transformFitnessToDistribution(allFitness,
					allProbabilities);
			double start = (generator.nextInt(randomRange) % 100000) / 100000;
			int[] perSelectCount = new int[allLength];
			Selection.selection(allProbabilities, para.initialPopulationCount,
					start, perSelectCount);
			int[][] nextGen = new int[para.initialPopulationCount][dnaLength];
			index = 0;
			int j = 0;
			for (i = 0; i < para.initialPopulationCount; i++)
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
				for (j = 0; j < perSelectCount[i + para.initialPopulationCount]; j++)
				{
					System.arraycopy(newDNAs[i], 0, nextGen[index], 0,
							dnaLength);
					index++;
				}
			}
			for (int p = 0; p < para.initialPopulationCount; p++)
				System.arraycopy(nextGen[p], 0, dnaMatrix[p], 0, dnaLength);
			int latestMinFitness = CaculateFitness.evaluate(dnaMatrix[0],
					dnaLength, input);
			int latestIndexMin = 0;
			for (i = 1; i < para.initialPopulationCount; i++)
			{
				fitness[i] = CaculateFitness.evaluate(dnaMatrix[i], dnaLength,
						input);
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

