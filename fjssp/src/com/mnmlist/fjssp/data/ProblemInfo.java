package com.mnmlist.fjssp.data;


public class ProblemInfo
{
	private int[]machineCountArr;//how many machines can choose for every operation
	private int[] operationCountArr;//how many operations for every job
	private int proDesMatrix[][];//the machine no and time for every operation
	private int machineCount; // total machine count
	private int jobCount; // total job count
	private int maxOperationCount = 0;//the max operation count for the whole job
	private int totalOperationCount=0;//the total operation count for the whole job
	private int[][] operationToIndex;// the index of some operation of some job
	private double crossoverRate=0.9;
	private double mutationRate=0.05;
	private int loopCount=1;
	private int populationCount=10;
	private int iteratorCount=5;
	private int timeLimit=-1;//no time limit
	private double gsRate=0.6;//global search rate
	private double lsRate=0.3;//local search rate
	private double rsRate=0.1;//random search rate
	public int[] getMachineCountArr()
	{
		return machineCountArr;
	}
	public void setMachineCountArr(int[] machineCountArr)
	{
		this.machineCountArr = machineCountArr;
	}
	public int getLoopCount()
	{
		return loopCount;
	}
	public double getGsRate()
	{
		return gsRate;
	}

	public double getLsRate()
	{
		return lsRate;
	}

	public double getRsRate()
	{
		return rsRate;
	}

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

	//Random random=new Random();
	public double getCrossoverRate()
	{
		return crossoverRate;
	}

	public double getMutationRate()
	{
		return mutationRate;
	}

//	public Random getRandom()
//	{
//		return random;
//	}

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

	public int getTotalOperationCount()
	{
		return totalOperationCount;
	}

	public void setTotalOperationCount(int totalOperationCount)
	{
		this.totalOperationCount = totalOperationCount;
	}
}
