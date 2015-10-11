package com.mnmlist.fjssp.test;

import java.io.File;

import com.mnmlist.fjssp.data.ProblemInfo;
import com.mnmlist.fjssp.logic.InitProblemDescription;

public class TestGetProblemDes
{
	
	public static void main(String[] args)
	{
		File file=new File("mk01.txt");
		ProblemInfo ProblemInfo=InitProblemDescription.getProblemDesFromFile(file);
		int proDesMatrix[][]=ProblemInfo.getProDesMatrix();
		for(int i=0;i<proDesMatrix.length;i++)
		{
			for(int j=0;j<proDesMatrix[0].length;j++)
			{
				System.out.print(proDesMatrix[i][j]+" ");
			}
			System.out.println();
		}
		//InitProblemDescription.localSearch(ProblemInfo);
		InitProblemDescription.randomSearch(ProblemInfo);
		//InitProblemDescription.globalSearch(ProblemInfo);
	}

}
