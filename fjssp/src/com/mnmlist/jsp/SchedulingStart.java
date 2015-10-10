package com.mnmlist.jsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
		ProblemInput input = SchedulingStart.getInputFromFile();
		//get the input parameter ,such as the order,the job
		ParameterInput para = SchedulingStart.getParameter();
		SchedulingStart.schedulingBegin(input, para);
	}

	/**
	 * @param file a .txt file which contains the time and order information
	 * @return BufferedReader of the file
	 */
	static BufferedReader getBufferedReader(File file)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return reader;
	}
	/**
	 * @param problemInput  the problem description which has been arranged
	 */
	public static void localSearch(ProblemInputII problemInput)
	{
		
	}
	/**
	 * @param problemInput  the problem description which has been arranged
	 */
	public static void randomSearch(ProblemInputII problemInput)
	{
		
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
	 * @return input:the time and order information of the problem
	 */
	public static ProblemInput getInputFromFile()
	{
		String fileName = "beasley/mt06.txt";
		File file=new File(fileName);
		BufferedReader reader = getBufferedReader(file);
		int jobNum = 0;
		int operationNum = 0;
		ProblemInput input = null;
		String string = null;
		// 处理测试用例
		try
		{
			String tempArr[] = new String[operationNum * 2];
			string = reader.readLine();
			string = string.trim();
			tempArr = string.split(" ");
			jobNum = Integer.valueOf(tempArr[0]);
			operationNum = Integer.valueOf(tempArr[1]);
			input = new ProblemInput(jobNum, operationNum);
			input.machineCount = operationNum;// 暂定
			int len = operationNum * 2;
			int time = 0, machineNo = 0;
			for (int i = 0; i < jobNum; i++)
			{
				string = reader.readLine();
				string = string.trim();
				string = string.replaceAll("  ", " ");
				tempArr = string.split(" ");
				for (int j = 0; j < len; j += 2)
				{
					machineNo = Integer.valueOf(tempArr[j]);
					time = Integer.valueOf(tempArr[j + 1]);
					input.machineNoMatrix[i][j / 2] = machineNo;
					input.timeMatrix[i][j / 2] = time;
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				reader.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return input;
	}
	
	/**
	 * @return para:the information of the scheduling problem,such as populationCount,crossoverRate,mutationRate
	 */
	public static ParameterInput getParameter()
	{
		ParameterInput para = new ParameterInput(); // initialize ga parameter
		para.initialPopulationCount = 200;
		para.crossoverRate = 0.95;
		para.mutationRate = 0.05;
		para.timeLimit = -1; // -1 means no time limit
		para.iterationLimit = 1000; // 每次迭代寻找最优解所循环的次数
		System.out.println("time limit is : " + para.timeLimit);
		System.out.println("iterator    limit is : " + para.iterationLimit);
		System.out.println("population  is       : "
				+ para.initialPopulationCount);
		return para;
	}
	
	/**
	 * @param input:the time and order information of the input matrix
	 * @param para:the information of the scheduling problem,such as populationCount,crossoverRate,mutationRate
	 */
	public static void schedulingBegin(ProblemInput input, ParameterInput para)
	{
		GA jsspProblem = new GA();
		int currentBestChromesome[];
		int bestChromesome[] = new int[2000];// init is 2000
		int bestFitness = 0;
		int currentBestFitness = 0;
		int loopNum = 30;// 迭代次数
		for (int i = 0; i < loopNum; i++)
		{
			BestSolution bestSolution = jsspProblem.solve(para, input);
			currentBestChromesome = bestSolution.getBestChromesome();
			currentBestFitness = bestSolution.getBestFitness();
			if (i == 0)
			{
				bestChromesome = currentBestChromesome;
				bestFitness = currentBestFitness;
			} else
			{
				if (currentBestFitness < bestFitness)
				{
					bestFitness = currentBestFitness;
					System.arraycopy(currentBestChromesome, 0, bestChromesome,
							0, jsspProblem.dnaLen);
				}
			}
			System.out.print("In " + (i + 1)
					+ " generation,the current fitness is:"
					+ currentBestFitness);
			System.out.println(" After " + (i + 1)
					+ " generation,the best fitness is:" + bestFitness);
			for (int num : currentBestChromesome)
				System.out.print(num + "\t");
			System.out.println();
		}
		// 输出最优的测试用例
		System.out.println("After " + loopNum
				+ "generation the best fitness is " + bestFitness);
		System.out.println("the final and best chromesome is as follows...");
		for (int i = 0; i < jsspProblem.dnaLen; i++)
			System.out.print(bestChromesome[i] + "\t");
		System.out.println();
		CaculateFitness.evaluatePrint(bestChromesome, bestChromesome.length,
				input);
	}
}