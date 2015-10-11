package com.mnmlist.fjssp.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mnmlist.fjssp.data.ProblemInfo;

public class InitProblemDescription
{

	/**
	 * @param file
	 *            a .txt file which contains the time and order information
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
	 * @param problemInput
	 * 			 the problem description which has been arranged
	 * @return	
	 * 			a feasible solution merely include machine sequence
	 */
	public static int[] localSearch(ProblemInfo problemInput)
	{
		int proDesMatrix[][] = problemInput.getProDesMatrix();
		int machineCount = problemInput.getMachineCount();
		int jobCount = problemInput.getJobCount();
		int machineTimeArr[] = new int[machineCount];
		int machineSequenceLen = proDesMatrix.length;
		int machineNoSequence[] = new int[machineSequenceLen];
		int[][] operationToIndex = problemInput.getOperationToIndex();// 某工件工序所对应的index
		int start = 0, end = 0;
		int jobNo = 0;
		while (jobNo < jobCount)
		{
			start = operationToIndex[jobNo][0];
			if (jobNo + 1 < jobCount)
				end = operationToIndex[jobNo + 1][0] - 1;
			else
			{
				end = proDesMatrix.length - 1;
			}
			int minIndex = 0, j = 0, minTime = 0, machineEncode = 0;
			for (int i = start; i <= end; i++)
			{
				j = 0;
				while (j < machineCount && proDesMatrix[i][j] == 0)
					j++;
				minIndex = j;
				minTime = machineTimeArr[j] + proDesMatrix[i][j];
				j++;
				while (j < machineCount)
				{
					if (proDesMatrix[i][j] != 0
							&& machineTimeArr[j] + proDesMatrix[i][j] < minTime)
					{
						minIndex = j;
						minTime = machineTimeArr[j] + proDesMatrix[i][j];
					}
					j++;
				}
				machineTimeArr[minIndex] = minTime;// update the machine time
													// array
				// caculate the machine encode
				j = 0;
				machineEncode = 0;
				while (j <= minIndex)
				{
					if (proDesMatrix[i][j] != 0 && j <= minIndex)
						machineEncode++;
					j++;
				}
				machineNoSequence[i] = machineEncode;// the gene of the
														// machinesequence
			}
			Arrays.fill(machineTimeArr, 0);
			jobNo++;
		}
		return machineNoSequence;
	}

	/**
	 * @param problemInput
	 *            the problem description which has been arranged
	 * @return    a feasible solution merely include machine sequence
	 */
	public static int[] randomSearch(ProblemInfo problemInput)
	{
		int proDesMatrix[][] = problemInput.getProDesMatrix();
		int jobCount = problemInput.getJobCount();
		int machineSequenceLen = proDesMatrix.length;
		int machineSequence[] = new int[machineSequenceLen];
		int[][] operationToIndex = problemInput.getOperationToIndex();// 某工件工序所对应的index
		int operationCountArr[] = problemInput.getOperationCountArr();
		int start = 0, end = 0;
		int jobNo = 0;
		Random random = new Random();
		int count = 0, index = 0;
		int j = 0;
		while (jobNo < jobCount)
		{
			start = operationToIndex[jobNo][0];
			if (jobNo + 1 < jobCount)
				end = operationToIndex[jobNo + 1][0] - 1;
			else
			{
				end = proDesMatrix.length - 1;
			}
			for (int i = start; i <= end; i++)
			{
				count = random.nextInt(operationCountArr[i]) + 1;
				j = 0;
				index = 0;
				while (j < count)
				{
					if (proDesMatrix[i][index] != 0)
						j++;
					index++;
				}
				index--;
				machineSequence[i] = count;
			}
			jobNo++;
		}
		return machineSequence;
	}

	/**
	 * @param problemInput
	 *            the problem description which has been arranged
	 * @return    a feasible solution merely include machine sequence
	 */
	public static int[] globalSearch(ProblemInfo problemInput)
	{
		int proDesMatrix[][] = problemInput.getProDesMatrix();
		int machineCount = problemInput.getMachineCount();
		int jobCount = problemInput.getJobCount();
		int machineTimeArr[] = new int[machineCount];
		int machineSequenceLen = proDesMatrix.length;
		int machineSequence[] = new int[machineSequenceLen*2];
		int[][] operationToIndex = problemInput.getOperationToIndex();// 某工件工序所对应的index
		List<Integer> jobNoList = new ArrayList<Integer>();
		for (int i = 0; i < jobCount; i++)
			jobNoList.add(i);
		Random random = new Random();
		int randomIndex = 0;
		int listSize = jobNoList.size();
		int start = 0, end = 0;
		int jobNo = 0;
		while (listSize > 0)
		{
			randomIndex = random.nextInt(listSize);
			System.out.println(randomIndex);
			jobNo = jobNoList.get(randomIndex);
			start = operationToIndex[jobNo][0];
			if (jobNo != jobCount - 1)
				end = operationToIndex[jobNo + 1][0] - 1;
			else
			{
				end = proDesMatrix.length - 1;
			}
			int minIndex = 0, j = 0, minTime = 0, machineEncode = 0;
			for (int i = start; i <= end; i++)
			{
				j = 0;
				while (j < machineCount && proDesMatrix[i][j] == 0)
					j++;
				minIndex = j;
				minTime = machineTimeArr[j] + proDesMatrix[i][j];
				j++;
				while (j < machineCount)
				{
					if (proDesMatrix[i][j] != 0
							&& machineTimeArr[j] + proDesMatrix[i][j] < minTime)
					{
						minIndex = j;
						minTime = machineTimeArr[j] + proDesMatrix[i][j];

					}
					j++;
				}
				machineTimeArr[minIndex] = minTime;// update the machine time
													// array
				// caculate the machine encode
				j = 0;
				machineEncode = 0;
				while (j <= minIndex)
				{
					if (proDesMatrix[i][j] != 0 && j <= minIndex)
						machineEncode++;
					j++;
				}
				machineSequence[i] = machineEncode;// the gene of the machine
													// sequence
			}
			jobNoList.remove(randomIndex);
			listSize = jobNoList.size();
		}
		return machineSequence;
	}

	/**
	 * @param file
	 *            the problem description stored location
	 * @return the problem description which has been arranged
	 */
	public static ProblemInfo getProblemDesFromFile(File file)
	{
		ProblemInfo ProblemInfo = new ProblemInfo();
		BufferedReader reader = getBufferedReader(file);
		String prodesStrArr[] = null;
		int proDesMatrix[][] = null;
		String proDesString;
		int[] operationCountArr = null;
		List<Integer> operationCountList = new ArrayList<Integer>();
		try
		{
			proDesString = reader.readLine();
			String proDesArr[] = proDesString.split("\\s+");
			int jobNum = Integer.valueOf(proDesArr[0]);
			int machineNum = Integer.valueOf(proDesArr[1]);
			ProblemInfo.setJobCount(jobNum);
			ProblemInfo.setMachineCount(machineNum);
			prodesStrArr = new String[jobNum];
			int count = 0;// caculate how many orders in the problem
			int index = 0;// store the index of first blank
			int maxOperationCount = 0, tempCount = 0;
			// operationCountArr=new int[jobNum];
			// find the max operation count of the job arrays
			for (int i = 0; i < jobNum; i++)
			{
				prodesStrArr[i] = reader.readLine().trim();
				index = prodesStrArr[i].indexOf(' ');
				tempCount = Integer
						.valueOf(prodesStrArr[i].substring(0, index));
				// operationCountArr[i]=tempCount;
				count += tempCount;
				if (maxOperationCount < tempCount)
					maxOperationCount = tempCount;
			}
			int[][] operationToIndex = new int[jobNum][maxOperationCount];// 用来存储i工件j工序所对应的problemDesMatrix[][]的index
			ProblemInfo.setMaxOperationCount(maxOperationCount);
			// ProblemInfo.setOperationCountArr(operationCountArr);
			proDesMatrix = new int[count][];
			String opeationDesArr[];
			int operationCount = 0;
			int operationTotalIndex = 0;
			int selectedMachineCount = 0;
			int machineNo = 0, operationTime = 0;
			proDesMatrix[0] = new int[machineNum];
			for (int i = 0; i < jobNum; i++)
			{
				opeationDesArr = prodesStrArr[i].split("\\s+");
				operationCount = Integer.valueOf(opeationDesArr[0]);// the
																	// opeartion
																	// count of
																	// every job
				int k = 1;
				for (int j = 0; j < operationCount; j++)
				{
					if (k < opeationDesArr.length)
					{
						selectedMachineCount = Integer
								.valueOf(opeationDesArr[k++]);
						for (int m = 0; m < selectedMachineCount; m++)
						{
							machineNo = Integer.valueOf(opeationDesArr[k++]);
							operationTime = Integer
									.valueOf(opeationDesArr[k++]);
							proDesMatrix[operationTotalIndex][machineNo - 1] = operationTime;
						}
						operationCountList.add(selectedMachineCount);// 存储每个工序的备选机器数目
					}
					operationToIndex[i][j] = operationTotalIndex;// 用来存储i工件j工序所对应的problemDesMatrix[][]的index
					operationTotalIndex++;
					if (operationTotalIndex < count)
					{
						proDesMatrix[operationTotalIndex] = new int[machineNum];
					}

				}
			}
			operationCountArr = new int[operationTotalIndex];
			int listSize = operationCountList.size();
			for (int i = 0; i < listSize; i++)
				operationCountArr[i] = operationCountList.get(i);
			ProblemInfo.setProDesMatrix(proDesMatrix);
			ProblemInfo.setTotalOperationCount(proDesMatrix.length);
			ProblemInfo.setOperationToIndex(operationToIndex);
			ProblemInfo.setOperationCountArr(operationCountArr);
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return ProblemInfo;
	}
}
