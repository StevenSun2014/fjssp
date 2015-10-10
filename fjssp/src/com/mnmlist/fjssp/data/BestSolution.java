package com.mnmlist.fjssp.data;

public class BestSolution
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
