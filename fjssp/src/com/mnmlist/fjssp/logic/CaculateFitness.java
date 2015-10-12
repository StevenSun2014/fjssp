package com.mnmlist.fjssp.logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;

import com.mnmlist.fjssp.data.Operation;
import com.mnmlist.fjssp.data.ProblemInfo;
import com.mnmlist.fjssp.lib.UtilLib;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class CaculateFitness
{
	public static void main(String args[])
	{
		
		int dnaSeq[]={1, 3, 2, 1, 1, 3, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 1, 1, 3, 2, 3, 1, 1, 2, 2, 2, 2, 1, 2, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 1, 1, 2, 1, 1, 3, 1, 2, 2, 2, 3, 3, 1, 2, 1, 1, 2, 0, 9, 6, 5, 8, 9, 5, 5, 8, 4, 0, 2, 5, 9, 8, 3, 1, 5, 8, 9, 0, 9, 6, 9, 8, 6, 1, 2, 3, 8, 2, 4, 5, 7, 7, 6, 1, 3, 7, 1, 7, 4, 4, 2, 0, 3, 6, 4, 0, 4, 3, 7, 0};
		System.out.println(dnaSeq.length);
		for(int i=0;i<55;i++)
		{
			if(i%20==0)
				System.out.println();
			System.out.print(dnaSeq[i]+", ");
		}	
		for(int i=0;i<55;i++)
		{
			if(i%20==0)
				System.out.println();
			System.out.print(dnaSeq[i+55]+", ");
		}
		File file=new File("mk01.txt");
		ProblemInfo input=InitProblemDescription.getProblemDesFromFile(file);
		int dnaLen=dnaSeq.length;
		int length=dnaLen/2;
		int jobNo=0,operNo=0;
		int jobCount=input.getJobCount();
		int machineNoAndTimeArr[]=new int[2];
		int[] operNoOfEachJob = new int[jobCount];// 工种数
		for (int i = length; i < dnaLen; i++)
		{
			jobNo = dnaSeq[i];// 工件名
			operNo = operNoOfEachJob[jobNo]++;// 当前工件操作所在的工序数
			getMachineNoAndTime(input, dnaSeq, jobNo, operNo,machineNoAndTimeArr);
		}
	}
	/**
	 * @param ProblemInfo
	 *            the problem description which has been arranged
	 */
	public static void getMachineNoAndTime(ProblemInfo input,int dnaSeq[],
			int jobNo,int operationNo,int machineNoAndTimeArr[])
	{
//		System.out.println(Arrays.toString(dnaSeq));
//		System.out.println(dnaSeq.length);
		int[][] proDesMatrix = input.getProDesMatrix();
		int operationToIndex[][]=input.getOperationToIndex();
		int tempCount = 0, index = 0;
		int totaloperNo = operationToIndex[jobNo][operationNo];
		int machineTimeArr[]=proDesMatrix[totaloperNo];
		index = 0;
		int count = dnaSeq[totaloperNo];
//		if(count==6)
//		{
//			System.out.println("break:"+Arrays.toString(dnaSeq));
//		}
			
		while (tempCount < count)
		{
//			if(index==6)
//				System.out.println("break.");
			if (machineTimeArr[index] != 0)
				tempCount++;
			index++;
		}
//		System.out.println("tempCount"+tempCount+"Count:"+count);
		index--;
		machineNoAndTimeArr[0]=index;
		machineNoAndTimeArr[1]=proDesMatrix[totaloperNo][index];
	}
	/**
	 * 计算一条染色体（一个可行的调度）所耗费的最大时间
	 * @param dna the dna array,an element represents a procedure of a job
	 * @param length the DNA array length
	 * @param input the time and order information of the problem
	 * @return the fitness of a sheduling
	 */
	public static int evaluate(int[] dna,ProblemInfo input)
	{
		int length=dna.length/2;
		int dnaLen=dna.length;
		int jobCount=input.getJobCount();
		int operCount=input.getMaxOperationCount();
		int machineCount=input.getMachineCount();
		int span = -1;
		int[] operNoOfEachJob = new int[jobCount];// 工种数
		int[] machFreeTime = new int[machineCount];// 机器数
		Operation[][] jobOperMatrix = new Operation[jobCount][operCount];
		for (int p = 0; p < jobCount; p++)
			for (int q = 0; q < operCount; q++)
				jobOperMatrix[p][q] = new Operation();
		int i = 0;
		int jobNo = 0;
		int operNo = 0;
		int operationTime = 0;
		int machineNo = 0;
		int machineNoAndTimeArr[]=new int[2];
		for (i = length; i < dnaLen; i++)
		{
			jobNo = dna[i];// 工件名
			operNo = operNoOfEachJob[jobNo]++;// 当前工件操作所在的工序数
			getMachineNoAndTime(input, dna, jobNo, operNo,machineNoAndTimeArr);
			machineNo=machineNoAndTimeArr[0];
			operationTime=machineNoAndTimeArr[1];
			if (operNo == 0)
			{
				jobOperMatrix[jobNo][operNo].jobNo = jobNo;
				jobOperMatrix[jobNo][operNo].operationNo = operNo;
				// jobOperMatrix[jobNo][operNo].machineNo
				// = machineNo;
				jobOperMatrix[jobNo][operNo].startTime = machFreeTime[machineNo];
				jobOperMatrix[jobNo][operNo].endTime = jobOperMatrix[jobNo][operNo].startTime
						+ operationTime;
			} else
			{
				jobOperMatrix[jobNo][operNo].jobNo = jobNo;
				jobOperMatrix[jobNo][operNo].operationNo = operNo;
				// jobOperMatrix[jobNo][operNo].machineNo
				// = machineNo;
				jobOperMatrix[jobNo][operNo].startTime = UtilLib
						.max(jobOperMatrix[jobNo][operNo - 1].endTime,
								machFreeTime[machineNo]);
				jobOperMatrix[jobNo][operNo].endTime = jobOperMatrix[jobNo][operNo].startTime
						+ operationTime;
			}
			machFreeTime[machineNo] = jobOperMatrix[jobNo][operNo].endTime;
			if (jobOperMatrix[jobNo][operNo].endTime > span)
			{
				span = jobOperMatrix[jobNo][operNo].endTime;
			}
		}

		return span;
	}
	/**
	 * @param dna:the dna array,an element represents a procedure of a job
	 * @param length:the DNA array length
	 * @param input:the time and order information of the problem
	 * @return the fitness of a sheduling
	 */
	public static int evaluatePrint(int[] dna, ProblemInfo input)
	{
		int length=dna.length/2;
		int dnaLen=dna.length;
		int jobCount=input.getJobCount();
		int operCount=input.getMaxOperationCount();
		int machineCount=input.getMachineCount();
		StringBuilder jobNoBuilder = new StringBuilder();
		StringBuilder machineNoBuilder = new StringBuilder();
		StringBuilder startTimeBuilder = new StringBuilder();
		StringBuilder endTimeBuilder = new StringBuilder();
		int span = -1;
		int[] operNoOfEachJob = new int[jobCount];
		int[] machFreeTime = new int[machineCount];
		Operation[][] jobOperMatrix = new Operation[jobCount][operCount];
		for (int p = 0; p < jobCount; p++)
			for (int q = 0; q < operCount; q++)
				jobOperMatrix[p][q] = new Operation();
		// operation schedule[input->machineCount][input->jobCount];
		int jobNo = 0;
		int operNo = 0;
		int operationTime = 0;
		int machineNo = 0;
		int start = 0, end = 0;
		int machineNoAndTimeArr[]=new int[2];
		for (int i = length; i < dnaLen; i++)
		{
			jobNo = dna[i];
			operNo = operNoOfEachJob[jobNo]++;
			getMachineNoAndTime(input, dna, jobNo, operNo,machineNoAndTimeArr);
			machineNo=machineNoAndTimeArr[0];
			operationTime=machineNoAndTimeArr[1];
			jobOperMatrix[jobNo][operNo].jobNo = jobNo;
			jobOperMatrix[jobNo][operNo].operationNo = operNo;
			// jobOperMatrix[jobNo][operNo].machineNo
			// = machineNo;
			if (operNo == 0)
				start = machFreeTime[machineNo];
			else
				start = UtilLib
						.max(jobOperMatrix[jobNo][operNo - 1].endTime,
								machFreeTime[machineNo]);
			jobOperMatrix[jobNo][operNo].startTime = start;
			end = jobOperMatrix[jobNo][operNo].startTime
					+ operationTime;
			jobOperMatrix[jobNo][operNo].endTime = end;
			machFreeTime[machineNo] = jobOperMatrix[jobNo][operNo].endTime;
			if (jobOperMatrix[jobNo][operNo].endTime > span)
			{
				span = jobOperMatrix[jobNo][operNo].endTime;
			}
			System.out.print("Machine:" + (machineNo + 1) + "|Job:"
					+ (jobNo + 1) + "|Gongxu:" + (operNo + 1));
			System.out.println("|p(" + (machineNo + 1) + ","
					+ (jobNo + 1) + ")=" + operationTime + "|s:"
					+ (start + 1) + "|e:" + end);
			jobNoBuilder.append(jobNo + " ");
			machineNoBuilder.append(machineNo + " ");
			startTimeBuilder.append(start + " ");
			endTimeBuilder.append(end - start + " ");
		}
		printSchedPicInConsole(input,dna, jobOperMatrix);
		// storeToDisk(machineNoBuilder, jobNoBuilder, startTimeBuilder,endTimeBuilder);
		return span;

	}

	/**
	 * @param machineNoBuilder:the machineNo which the procedure will be handled
	 * @param jobNoBuilder:the jobNo of the procedure
	 * @param startTimeBuilder:the start time of the procedure
	 * @param endTimeBuilder:the end time of the procedure
	 */
	public static void storeToDisk(StringBuilder machineNoBuilder,
			StringBuilder jobNoBuilder, StringBuilder startTimeBuilder,
			StringBuilder endTimeBuilder)
	{
		File file = new File("operationInfo.txt");// draw the picture which will
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(file);
			fw.write(machineNoBuilder.toString() + "\r\n");
			fw.write(jobNoBuilder.toString() + "\r\n");
			fw.write(startTimeBuilder.toString() + "\r\n");
			fw.write(endTimeBuilder.toString() + "\r\n");
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param input:the time and order information of the problem
	 * @param jobOperMatrix:the handle process of the scheduling
	 * problem,which will include operationNo,jobNo,startTime,endTime
	 */
	public static void printSchedPicInConsole(ProblemInfo input,int []dna,
			Operation[][] jobOperMatrix)
	{
		int start = 0, end = 0, machineNo = 0;
		String flagString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// 控制台输出图形
		Operation tempOperation = null;
		int j = 0, p = 0, q = 0, i = 0;
		char ch = 'a';
		int machineCount = input.getMachineCount();
		int colums = 2000;
		char sheduleMatrix[][] = new char[machineCount][colums];
		for (i = 0; i < machineCount; i++)
			Arrays.fill(sheduleMatrix[i], ' ');
		machineNo = 0;
		int jobCount=input.getJobCount();
		int operCount=input.getMaxOperationCount();
		int machineNoAndTimeArr[]=new int[2];
		for (p = 0; p < jobCount; p++)
		{
			ch = flagString.charAt(p);// 每一个工件对应一种字符
			for (q = 0; q < operCount; q++)
			{
				tempOperation = jobOperMatrix[p][q];
				start = tempOperation.startTime;
				end = tempOperation.endTime;
				getMachineNoAndTime(input,dna,p,q,machineNoAndTimeArr);
				machineNo=machineNoAndTimeArr[0];
				if (machineNo == -1)
					continue;
				for (j = start; j < end; j++)
					sheduleMatrix[machineNo][j] = ch;
			}

		}
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter(System.out);
		formatter.format("%15.15s", "x coordinate:");
		for (j = 0; j < colums; j++)
		{
			if ((j + 1) % 5 == 0)
				formatter.format("%5s", "" + (j + 1));
		}
		// System.out.print((j + 1) % 10);
		System.out.println();
		for (i = 0; i < machineCount; i++)
		{
			formatter.format("%15.15s", "Machine " + (i + 1) + ":");
			for (j = 0; j < colums; j++)
				System.out.print(sheduleMatrix[i][j]);
			System.out.println();
		}
		formatter.format("%15.15s", "x coordinate:");
		for (j = 0; j < colums; j++)
		{
			if ((j + 1) % 5 == 0)
				formatter.format("%5s", "" + (j + 1));
		}
		System.out.println();
	}
}
