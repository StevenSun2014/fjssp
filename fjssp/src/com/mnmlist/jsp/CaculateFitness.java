package com.mnmlist.jsp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class CaculateFitness
{
	/**
	 * @param problemInputII
	 *            the problem description which has been arranged
	 */
	public static int [] getMachineNoAndTime(ProblemInputII problemInputII,int dnaSeq[],int jobNo,int operationNo)
	{
		int machineNoAndTimeArr[]=new int[2];
		int[][] proDesMatrix = problemInputII.getProDesMatrix();
		int operationToIndex[][]=problemInputII.getOperationToIndex();
		int count = 0, tempCount = 0, index = 0;
		int totalOperationIndex = operationToIndex[jobNo][operationNo];
		index = 0;
		tempCount = 0;
		count = dnaSeq[totalOperationIndex];
		while (tempCount < count)
		{
			if (proDesMatrix[totalOperationIndex][index] != 0)
				tempCount++;
			index++;
		}
		index--;
		// machineNo=index,time=prodesMatrix[i][index]
		machineNoAndTimeArr[0]=index;
		machineNoAndTimeArr[1]=proDesMatrix[totalOperationIndex][index];
		return machineNoAndTimeArr;
	}
	/**
	 * 计算一条染色体（一个可行的调度）所耗费的最大时间
	 * @param dna:the dna array,an element represents a procedure of a job
	 * @param length:the DNA array length
	 * @param input:the time and order information of the problem
	 * @return the fitness of a sheduling
	 */
	public static int evaluate(int[] dna, int length, ProblemInput input)
	{
		int span = -1;
		int[] operationIndexOfEachJob = new int[input.jobCount];// 工种数
		int[] machineLastestFreeTime = new int[input.machineCount];// 机器数
		Operation[][] gongjianGongxuOperationMatrix = new Operation[input.jobCount][input.operationCount];
		for (int p = 0; p < input.jobCount; p++)
			for (int q = 0; q < input.operationCount; q++)
				gongjianGongxuOperationMatrix[p][q] = new Operation();
		int i = 0;
		int gongjianName = 0;
		int operationIndex = 0;
		int operationTime = 0;
		int machineNo = 0;
		for (i = 0; i < length; i++)
		{
			gongjianName = dna[i];// 工件名
			operationIndex = operationIndexOfEachJob[gongjianName]++;// 当前工件操作所在的工序数
			operationTime = input.timeMatrix[gongjianName][operationIndex];// 某个工序所花费的时间
			machineNo = input.machineNoMatrix[gongjianName][operationIndex];// 某个工序所在机器号
			if (operationIndex == 0)
			{
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].jobNo = gongjianName;
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].operationNo = operationIndex;
				// gongjianGongxuOperationMatrix[gongjianName][operationIndex].machineNo
				// = machineNo;
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].startTime = machineLastestFreeTime[machineNo];
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime = gongjianGongxuOperationMatrix[gongjianName][operationIndex].startTime
						+ operationTime;
			} else
			{
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].jobNo = gongjianName;
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].operationNo = operationIndex;
				// gongjianGongxuOperationMatrix[gongjianName][operationIndex].machineNo
				// = machineNo;
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].startTime = UtilLib
						.max(gongjianGongxuOperationMatrix[gongjianName][operationIndex - 1].endTime,
								machineLastestFreeTime[machineNo]);
				gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime = gongjianGongxuOperationMatrix[gongjianName][operationIndex].startTime
						+ operationTime;
			}
			machineLastestFreeTime[machineNo] = gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime;
			if (gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime > span)
			{
				span = gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime;
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
	public static int evaluatePrint(int[] dna, int length, ProblemInput input)
	{
		StringBuilder jobNoBuilder = new StringBuilder();
		StringBuilder machineNoBuilder = new StringBuilder();
		StringBuilder startTimeBuilder = new StringBuilder();
		StringBuilder endTimeBuilder = new StringBuilder();
		int span = -1;
		int[] operationIndexOfEachJob = new int[input.jobCount];
		int[] machineLastestFreeTime = new int[input.machineCount];
		Operation[][] gongjianGongxuOperationMatrix = new Operation[input.jobCount][input.operationCount];
		for (int p = 0; p < input.jobCount; p++)
			for (int q = 0; q < input.operationCount; q++)
				gongjianGongxuOperationMatrix[p][q] = new Operation();
		// operation schedule[input->machineCount][input->jobCount];
		int gongjianName = 0;
		int operationIndex = 0;
		int operationTime = 0;
		int machineNo = 0;
		int start = 0, end = 0;
		for (int i = 0; i < length; i++)
		{
			gongjianName = dna[i];
			operationIndex = operationIndexOfEachJob[gongjianName]++;
			operationTime = input.timeMatrix[gongjianName][operationIndex];
			machineNo = input.machineNoMatrix[gongjianName][operationIndex];
			gongjianGongxuOperationMatrix[gongjianName][operationIndex].jobNo = gongjianName;
			gongjianGongxuOperationMatrix[gongjianName][operationIndex].operationNo = operationIndex;
			// gongjianGongxuOperationMatrix[gongjianName][operationIndex].machineNo
			// = machineNo;
			if (operationIndex == 0)
				start = machineLastestFreeTime[machineNo];
			else
				start = UtilLib
						.max(gongjianGongxuOperationMatrix[gongjianName][operationIndex - 1].endTime,
								machineLastestFreeTime[machineNo]);
			gongjianGongxuOperationMatrix[gongjianName][operationIndex].startTime = start;
			end = gongjianGongxuOperationMatrix[gongjianName][operationIndex].startTime
					+ operationTime;
			gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime = end;
			machineLastestFreeTime[machineNo] = gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime;
			if (gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime > span)
			{
				span = gongjianGongxuOperationMatrix[gongjianName][operationIndex].endTime;
			}
			System.out.print("Machine:" + (machineNo + 1) + "|Job:"
					+ (gongjianName + 1) + "|Gongxu:" + (operationIndex + 1));
			System.out.println("|p(" + (machineNo + 1) + ","
					+ (gongjianName + 1) + ")=" + operationTime + "|s:"
					+ (start + 1) + "|e:" + end);
			jobNoBuilder.append(gongjianName + " ");
			machineNoBuilder.append(machineNo + " ");
			startTimeBuilder.append(start + " ");
			endTimeBuilder.append(end - start + " ");
		}
		printSchedPicInConsole(input, gongjianGongxuOperationMatrix);
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
	 * @param gongjianGongxuOperationMatrix:the handle process of the scheduling
	 * problem,which will include operationNo,jobNo,startTime,endTime
	 */
	public static void printSchedPicInConsole(ProblemInput input,
			Operation[][] gongjianGongxuOperationMatrix)
	{
		int start = 0, end = 0, machineNo = 0;
		String flagString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// 控制台输出图形
		Operation tempOperation = null;
		int j = 0, p = 0, q = 0, i = 0;
		char ch = 'a';
		int machineCount = input.machineCount;
		int colums = 2000;
		char sheduleMatrix[][] = new char[machineCount][colums];
		for (i = 0; i < machineCount; i++)
			Arrays.fill(sheduleMatrix[i], ' ');
		machineNo = 0;
		for (p = 0; p < input.jobCount; p++)
		{
			ch = flagString.charAt(p);// 每一个工件对应一种字符
			for (q = 0; q < input.operationCount; q++)
			{
				tempOperation = gongjianGongxuOperationMatrix[p][q];
				start = tempOperation.startTime;
				end = tempOperation.endTime;
				machineNo = input.machineNoMatrix[p][q];// pay attention to it
														// please
				if (machineNo == -1)
					continue;
				for (j = start; j < end; j++)
					sheduleMatrix[machineNo][j] = ch;
			}

		}
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
