package com.mnmlist.jsp;

public class FeasibleSolution
{
	int[][] proDesMatrix = null;
	private int maxOperationCount;
	private int jobCount;
	private int machineNoSequence[] = null;
	private int operationSequence[] = null;
	int machineNoMatrix[][] = null;
	int timeMatrix[][] = null;
	int crossStart;
	int crossEnd;
	int mutationStart;
	int mutationEnd;

	public FeasibleSolution(ProblemInputII problemInputII)
	{
		proDesMatrix = problemInputII.getProDesMatrix();
		maxOperationCount = problemInputII.getMaxOperationCount();
		jobCount = problemInputII.getJobCount();
		machineNoSequence = new int[maxOperationCount];
		operationSequence = new int[maxOperationCount];
		machineNoMatrix = new int[jobCount][maxOperationCount];
		timeMatrix = new int[jobCount][maxOperationCount];
	}

	public int getCrossStart()
	{
		return crossStart;
	}

	public void setCrossStart(int crossStart)
	{
		this.crossStart = crossStart;
	}

	public int getCrossEnd()
	{
		return crossEnd;
	}

	public void setCrossEnd(int crossEnd)
	{
		this.crossEnd = crossEnd;
	}

	public int getMutationStart()
	{
		return mutationStart;
	}

	public void setMutationStart(int mutationStart)
	{
		this.mutationStart = mutationStart;
	}

	public int getMutationEnd()
	{
		return mutationEnd;
	}

	public void setMutationEnd(int mutationEnd)
	{
		this.mutationEnd = mutationEnd;
	}

	public int[][] getProDesMatrix()
	{
		return proDesMatrix;
	}

	public int getMaxOperationCount()
	{
		return maxOperationCount;
	}

	public int getJobCount()
	{
		return jobCount;
	}

	public int[] getMachineNoSequence()
	{
		return machineNoSequence;
	}

	public void setMachineNoSequence(int[] machineNoSequence)
	{
		this.machineNoSequence = machineNoSequence;
	}

	public int[] getOperationSequence()
	{
		return operationSequence;
	}

	public void setOperationSequence(int[] operationSequence)
	{
		this.operationSequence = operationSequence;
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

}
