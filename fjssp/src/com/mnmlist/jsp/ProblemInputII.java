package com.mnmlist.jsp;

public class ProblemInputII
{
	private int[] operationCountArr;
	private int proDesMatrix[][];
	private int machineNoMatrix[][];// �����ž���
	private int timeMatrix[][]; // ʱ�����
	private int machineCount; // ������
	private int jobCount; // ��ҵ��Ŀ
	private int maxOperationCount = 0;
	int[][] operationToIndex;// ĳ������ĳ�����Ӧ��index

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
