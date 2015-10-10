package com.mnmlist.jsp;

public class ProblemInputII
{
	private int[] operationCountArr;
	private int proDesMatrix[][];
	private int machineNoMatrix[][];// 机器号矩阵
	private int timeMatrix[][]; // 时间矩阵
	private int machineCount; // 机器数
	private int jobCount; // 作业数目
	private int maxOperationCount = 0;
	int[][] operationToIndex;// 某工件的某工序对应的index

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

	public int[][] getMachineNoMatrix()
	{
		return machineNoMatrix;
	}

	public void setMachineNoMatrix(int[][] machineNoMatrix)
	{
		this.machineNoMatrix = machineNoMatrix;
	}

	public int[][] getTimeMatrix()
	{
		return timeMatrix;
	}

	public void setTimeMatrix(int[][] timeMatrix)
	{
		this.timeMatrix = timeMatrix;
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
