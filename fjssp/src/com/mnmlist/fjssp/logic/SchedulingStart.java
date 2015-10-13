package com.mnmlist.fjssp.logic;

import java.io.File;
import java.util.Arrays;

import com.mnmlist.fjssp.data.BestSolution;
import com.mnmlist.fjssp.data.ProblemInfo;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
class SchedulingStart
{
	public static void main(String args[])
	{
		//get the problem description,such as populationCount,crossoverRate,mutationRate
		File file=new File("mk01.txt");
		ProblemInfo input=InitProblemDescription.getProblemDesFromFile(file);
		//get the input parameter ,such as the order,the job
		SchedulingStart.schedulingBegin(input);
	}

	/**
	 * @param proDesMatrix the machine and time on some machine
	 * @return the machine enconding array
	 */
	public static int []getAachineArr(int [][]proDesMatrix )
	{
		int machineArr[]=new int[proDesMatrix.length];
		
		return machineArr;
	}
	/**
	 * @param input:the time and order information of the input matrix
	 * @param para:the information of the scheduling problem,such as populationCount,crossoverRate,mutationRate
	 */
	public static void schedulingBegin(ProblemInfo input)
	{
		FlexibleJobShop fjssp = new FlexibleJobShop();
		int currentBestChromesome[];
		int bestFitness = 0;
		int currentBestFitness = 0;
		int loopNum = input.getLoopCount();// 迭代次数
		int dnaLen=input.getTotalOperationCount()*2;
		int bestChromesome[] = new int[dnaLen];
		for (int i = 0; i < loopNum; i++)
		{
			BestSolution bestSolution = fjssp.solve( input);
			currentBestChromesome = bestSolution.getBestChromesome();
			currentBestFitness = bestSolution.getBestFitness();
//			int newFitness=CaculateFitness.evaluate(currentBestChromesome, input);
//			System.out.println("Old fitness:"+currentBestFitness+",New fitness:"+newFitness);
			if (i == 0)
			{
				bestFitness = currentBestFitness;
				System.arraycopy(currentBestChromesome, 0, bestChromesome,
						0, dnaLen);
			} else
			{
				if (currentBestFitness < bestFitness)
				{
					bestFitness = currentBestFitness;
					System.arraycopy(currentBestChromesome, 0, bestChromesome,
							0, dnaLen);
				}
			}
			System.out.print("In " + (i + 1)
					+ " generation,the current fitness is:"
					+ currentBestFitness);
			System.out.println(" After " + (i + 1)
					+ " generation,the best fitness is:" + bestFitness);
			System.out.println(Arrays.toString(currentBestChromesome));
		}
		// 输出最优的测试用例
		System.out.println("After " + loopNum
				+ " generation the best fitness is " + bestFitness);
		System.out.println("the final and best chromesome is as follows...");
		System.out.println(Arrays.toString(bestChromesome));
		CaculateFitness.evaluatePrint(bestChromesome,input);
	}
}