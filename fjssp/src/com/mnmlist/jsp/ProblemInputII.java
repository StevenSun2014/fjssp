package com.mnmlist.jsp;

import java.util.Random;

public class ProblemInputII
{
	private int[] operationCountArr;
	private int proDesMatrix[][];
	private int machineCount; // ������
	private int jobCount; // ��ҵ��Ŀ
	private int maxOperationCount = 0;
	int[][] operationToIndex;// ĳ������ĳ�����Ӧ��index
	Random random=new Random();
	public Random getRandom()
	{
		return random;
	}

	public void setRandom(Random random)
	{
		this.random = random;
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
