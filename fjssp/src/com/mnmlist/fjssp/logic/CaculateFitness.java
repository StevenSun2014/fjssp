package com.mnmlist.fjssp.logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;

import com.mnmlist.fjssp.data.Operation;
import com.mnmlist.fjssp.data.ProblemInputII;
import com.mnmlist.fjssp.lib.UtilLib;

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
	 * ����һ��Ⱦɫ�壨һ�����еĵ��ȣ����ķѵ����ʱ��
	 * @param dna:the dna array,an element represents a procedure of a job
	 * @param length:the DNA array length
	 * @param input:the time and order information of the problem
	 * @return the fitness of a sheduling
	 */
	public static int evaluate(int[] dna,ProblemInputII input)
	{
		int length=dna.length/2;
		int jobCount=input.getJobCount();
		int operCount=input.getMaxOperationCount();
		int machineCount=input.getMachineCount();
		int span = -1;
		int[] operationIndexOfEachJob = new int[jobCount];// ������
		int[] machineLastestFreeTime = new int[machineCount];// ������
		Operation[][] jobOperMatrix = new Operation[jobCount][operCount];
		for (int p = 0; p < jobCount; p++)
			for (int q = 0; q < operCount; q++)
				jobOperMatrix[p][q] = new Operation();
		int i = 0;
		int gongjianName = 0;
		int operationIndex = 0;
		int operationTime = 0;
		int machineNo = 0;
		for (i = 0; i < length; i++)
		{
			gongjianName = dna[i];// ������
			operationIndex = operationIndexOfEachJob[gongjianName]++;// ��ǰ�����������ڵĹ�����
			int machNoTimeArr[]=getMachineNoAndTime(input, dna, gongjianName, operationIndex);
			machineNo=machNoTimeArr[0];
			operationTime=machNoTimeArr[1];
			if (operationIndex == 0)
			{
				jobOperMatrix[gongjianName][operationIndex].jobNo = gongjianName;
				jobOperMatrix[gongjianName][operationIndex].operationNo = operationIndex;
				// jobOperMatrix[gongjianName][operationIndex].machineNo
				// = machineNo;
				jobOperMatrix[gongjianName][operationIndex].startTime = machineLastestFreeTime[machineNo];
				jobOperMatrix[gongjianName][operationIndex].endTime = jobOperMatrix[gongjianName][operationIndex].startTime
						+ operationTime;
			} else
			{
				jobOperMatrix[gongjianName][operationIndex].jobNo = gongjianName;
				jobOperMatrix[gongjianName][operationIndex].operationNo = operationIndex;
				// jobOperMatrix[gongjianName][operationIndex].machineNo
				// = machineNo;
				jobOperMatrix[gongjianName][operationIndex].startTime = UtilLib
						.max(jobOperMatrix[gongjianName][operationIndex - 1].endTime,
								machineLastestFreeTime[machineNo]);
				jobOperMatrix[gongjianName][operationIndex].endTime = jobOperMatrix[gongjianName][operationIndex].startTime
						+ operationTime;
			}
			machineLastestFreeTime[machineNo] = jobOperMatrix[gongjianName][operationIndex].endTime;
			if (jobOperMatrix[gongjianName][operationIndex].endTime > span)
			{
				span = jobOperMatrix[gongjianName][operationIndex].endTime;
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
	public static int evaluatePrint(int[] dna, ProblemInputII input)
	{
		int length=dna.length/2;
		int jobCount=input.getJobCount();
		int operCount=input.getMaxOperationCount();
		int machineCount=input.getMachineCount();
		StringBuilder jobNoBuilder = new StringBuilder();
		StringBuilder machineNoBuilder = new StringBuilder();
		StringBuilder startTimeBuilder = new StringBuilder();
		StringBuilder endTimeBuilder = new StringBuilder();
		int span = -1;
		int[] operationIndexOfEachJob = new int[jobCount];
		int[] machineLastestFreeTime = new int[machineCount];
		Operation[][] jobOperMatrix = new Operation[jobCount][operCount];
		for (int p = 0; p < jobCount; p++)
			for (int q = 0; q < operCount; q++)
				jobOperMatrix[p][q] = new Operation();
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
			int machNoTimeArr[]=getMachineNoAndTime(input, dna, gongjianName, operationIndex);
			machineNo=machNoTimeArr[0];
			operationTime=machNoTimeArr[1];
			jobOperMatrix[gongjianName][operationIndex].jobNo = gongjianName;
			jobOperMatrix[gongjianName][operationIndex].operationNo = operationIndex;
			// jobOperMatrix[gongjianName][operationIndex].machineNo
			// = machineNo;
			if (operationIndex == 0)
				start = machineLastestFreeTime[machineNo];
			else
				start = UtilLib
						.max(jobOperMatrix[gongjianName][operationIndex - 1].endTime,
								machineLastestFreeTime[machineNo]);
			jobOperMatrix[gongjianName][operationIndex].startTime = start;
			end = jobOperMatrix[gongjianName][operationIndex].startTime
					+ operationTime;
			jobOperMatrix[gongjianName][operationIndex].endTime = end;
			machineLastestFreeTime[machineNo] = jobOperMatrix[gongjianName][operationIndex].endTime;
			if (jobOperMatrix[gongjianName][operationIndex].endTime > span)
			{
				span = jobOperMatrix[gongjianName][operationIndex].endTime;
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
	public static void printSchedPicInConsole(ProblemInputII input,int []dna,
			Operation[][] jobOperMatrix)
	{
		int start = 0, end = 0, machineNo = 0;
		String flagString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// ����̨���ͼ��
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
		for (p = 0; p < jobCount; p++)
		{
			ch = flagString.charAt(p);// ÿһ��������Ӧһ���ַ�
			for (q = 0; q < operCount; q++)
			{
				tempOperation = jobOperMatrix[p][q];
				start = tempOperation.startTime;
				end = tempOperation.endTime;
				machineNo=getMachineNoAndTime(input,dna,p,q)[0];
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
