package com.mnmlist.fjssp.data;

import java.util.Random;

public class ProblemInputII
{
	private int[] operationCountArr;
	private int proDesMatrix[][];
	private int machineCount; // 机器数
	private int jobCount; // 作业数目
	private int maxOperationCount = 0;
	private int[][] operationToIndex;// 某工件的某工序对应的index
	private double crossoverRate=0.9;
	private double mutationRate=0.05;
	private int populationCount=100;
	private int iteratorCount=200;
	private int timeLimit=-1;//no time limit
	public int getTimeLimit()
	{
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit)
	{
		this.timeLimit = timeLimit;
	}

	public int getPopulationCount()
	{
		return populationCount;
	}

	public int getIteratorCount()
	{
		return iteratorCount;
	}

	Random random=new Random();
	public double getCrossoverRate()
	{
		return crossoverRate;
	}

	public double getMutationRate()
	{
		return mutationRate;
	}

	public Random getRandom()
	{
		return random;
	}

	public int[] getOperationCountArr()
	{
		return operationCountArr;
	}

	public void setOperationCountArr(int[] operationCountArr)
	{
		this.operationCountArr = operationCountArr;
	}

	public int[][] getOperationToIndex()
	{
		return operationToIndex;
	}

	public void setOperationToIndex(int[][] operationToIndex)
	{
		this.operationToIndex = operationToIndex;
	}

	public int getMaxOperationCount()
	{
		return maxOperationCount;
	}

	public void setMaxOperationCount(int maxOperationCount)
	{
		this.maxOperationCount = maxOperationCount;
	}

	public int[][] getProDesMatrix()
	{
		return proDesMatrix;
	}

	public void setProDesMatrix(int[][] proDesMatrix)
	{
		this.proDesMatrix = proDesMatrix;
	}

	public int getMachineCount()
	{
		return machineCount;
	}

	public void setMachineCount(int machineCount)
	{
		this.machineCount = machineCount;
	}

	public int getJobCount()
	{
		return jobCount;
	}

	public void setJobCount(int jobCount)
	{
		this.jobCount = jobCount;
	}
}
