package com.mnmlist.fjssp.data;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 * store the best individuals and the min time
 */
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
