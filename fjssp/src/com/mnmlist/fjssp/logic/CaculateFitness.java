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
 * use for caculate the fitness of the individuals
 */
public class CaculateFitness
{
	public static void main(String args[])
	{
		int dna[]={1, 3, 2, 1, 1, 3, 1, 1, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 3, 2, 3, 1, 1, 1, 2, 3, 2, 1, 2, 1, 1, 2, 1, 2, 2, 3, 1, 1, 2, 1, 2, 2, 1, 2, 1, 1, 3, 2, 2, 2, 3, 1, 2, 2, 9, 5, 9, 8, 1, 7, 1, 3, 8, 5, 6, 4, 5, 3, 7, 7, 2, 1, 1, 0, 5, 1, 5, 8, 6, 7, 3, 2, 6, 9, 9, 2, 3, 4, 2, 5, 0, 6, 7, 0, 6, 4, 8, 9, 0, 8, 2, 4, 3, 0, 4, 0, 8, 9, 4};
//		int machineSeq[]=new int[55];
//		int operationSeq[]=new int[55];
//		System.arraycopy(dna, 0, machineSeq, 0, 55);
//		System.arraycopy(dna, 55, operationSeq, 0, 55);
//		System.out.println(Arrays.toString(machineSeq));
//		System.out.println(Arrays.toString(operationSeq));
//		int countArr[]=new int[10];
//		for(int i=55;i<110;i++)
//			countArr[dna[i]]++;
//		System.out.println(Arrays.toString(countArr));
		File file=new File("mk01.txt");
		//get the problem description,such as populationCount,crossoverRate,mutationRate
		ProblemInfo input=InitProblemDescription.getProblemDesFromFile(file);
//		evaluate(dna, input);
	}
	/**
	 * @param ProblemInfo
	 *            the problem description which has been arranged
	 */
	public static void getMachineNoAndTime(ProblemInfo input,int dnaSeq[],
			int jobNo,int operationNo,int machineNoAndTimeArr[])
	{
		//System.out.println(Arrays.toString(dnaSeq));
		int[][] proDesMatrix = input.getProDesMatrix();
		int operationToIndex[][]=input.getOperationToIndex();
		int tempCount = 0, index = 0;
		int totaloperNo = operationToIndex[jobNo][operationNo];
		int machineTimeArr[]=proDesMatrix[totaloperNo];
		index = 0;
		int count = dnaSeq[totaloperNo];
		while (tempCount < count)
		{
			if (machineTimeArr[index] != 0)
				tempCount++;
			index++;
		}
		index--;
		machineNoAndTimeArr[0]=index;
		machineNoAndTimeArr[1]=proDesMatrix[totaloperNo][index];
	}
	/**
	 * @param operationMatrix the operation description of the scheduling problem
	 */
	public static void initOperationMatrix(Operation[][] operationMatrix)
	{
		int i=0,j=0;
		for(i=0;i<operationMatrix.length;i++)
		{
			for(j=0;j<operationMatrix[0].length;j++)
				operationMatrix[i][j].initOperation();
		}
	}
	/**
	 * 计算一条染色体（一个可行的调度）所耗费的最大时间
	 * @param dna the dna array,an element represents a procedure of a job
	 * @param length the DNA array length
	 * @param input the time and order information of the problem
	 * @return the fitness of a sheduling
	 */
	public static int evaluate(int[] dna,ProblemInfo input,Operation[][] operationMatrix)
	{
		//System.out.println(Arrays.toString(dna));
		int length=dna.length/2;
		int dnaLen=dna.length;
		int jobCount=input.getJobCount();
		initOperationMatrix(operationMatrix);
		int machineCount=input.getMachineCount();
		int span = -1;
		int[] operNoOfEachJob = new int[jobCount];// 工种数
		int[] machFreeTime = new int[machineCount];// 机器数
		int i = 0;
		int jobNo = 0;
		int operNo = 0;
		int operationTime = 0;
		int machineNo = 0;
		int machineNoAndTimeArr[]=new int[2];
		for (i = length; i < dnaLen; i++)
		{
			//System.out.println(i);
			jobNo = dna[i];// 工件名
			operNo = operNoOfEachJob[jobNo]++;// 当前工件操作所在的工序数
			//System.out.println("i="+i+",JobNo "+jobNo+",OperNo "+operNo);
			getMachineNoAndTime(input, dna, jobNo, operNo,machineNoAndTimeArr);
			machineNo=machineNoAndTimeArr[0];
			operationTime=machineNoAndTimeArr[1];
			if (operNo == 0)
			{
				operationMatrix[jobNo][operNo].jobNo = jobNo;
				operationMatrix[jobNo][operNo].operationNo = operNo;
				operationMatrix[jobNo][operNo].startTime = machFreeTime[machineNo];
				operationMatrix[jobNo][operNo].endTime = operationMatrix[jobNo][operNo].startTime
						+ operationTime;
			} else
			{
				operationMatrix[jobNo][operNo].jobNo = jobNo;
				operationMatrix[jobNo][operNo].operationNo = operNo;
				operationMatrix[jobNo][operNo].startTime = UtilLib
						.max(operationMatrix[jobNo][operNo - 1].endTime,
								machFreeTime[machineNo]);
				operationMatrix[jobNo][operNo].endTime = operationMatrix[jobNo][operNo].startTime
						+ operationTime;
			}
			machFreeTime[machineNo] = operationMatrix[jobNo][operNo].endTime;
			if (operationMatrix[jobNo][operNo].endTime > span)
			{
				span = operationMatrix[jobNo][operNo].endTime;
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
	public static int evaluatePrint(int[] dna, ProblemInfo input,Operation[][] operationMatrix)
	{
		initOperationMatrix(operationMatrix);
		int length=dna.length/2;
		int dnaLen=dna.length;
		int jobCount=input.getJobCount();
		int machineCount=input.getMachineCount();
		StringBuilder jobNoBuilder = new StringBuilder();
		StringBuilder machineNoBuilder = new StringBuilder();
		StringBuilder startTimeBuilder = new StringBuilder();
		StringBuilder endTimeBuilder = new StringBuilder();
		int span = -1;
		int[] operNoOfEachJob = new int[jobCount];
		int[] machFreeTime = new int[machineCount];
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
			operationMatrix[jobNo][operNo].jobNo = jobNo;
			operationMatrix[jobNo][operNo].operationNo = operNo;
			if (operNo == 0)
				start = machFreeTime[machineNo];
			else
				start = UtilLib
						.max(operationMatrix[jobNo][operNo - 1].endTime,
								machFreeTime[machineNo]);
			operationMatrix[jobNo][operNo].startTime = start;
			end = start+ operationTime;
			operationMatrix[jobNo][operNo].endTime = end;
			machFreeTime[machineNo] = end;
			if (operationMatrix[jobNo][operNo].endTime > span)
			{
				span = operationMatrix[jobNo][operNo].endTime;
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
		printSchedPicInConsole(input,dna, operationMatrix);
		storeToDisk(machineNoBuilder, jobNoBuilder, startTimeBuilder,endTimeBuilder);
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
			fw.write("工序号(颜色):"+jobNoBuilder.toString() + "\r\n");
			fw.write("机器号(y轴):"+machineNoBuilder.toString() + "\r\n");
			fw.write("开始时间(x轴):"+startTimeBuilder.toString() + "\r\n");
			fw.write("持续时间(x轴方向距离):"+endTimeBuilder.toString() + "\r\n");
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
