package com.mnmlist.fjssp.test;

import java.io.File;

import com.mnmlist.fjssp.data.ProblemInputII;
import com.mnmlist.fjssp.logic.InitProblemDescription;

public class TestGetProblemDes
{
	
	public static void main(String[] args)
	{
		File file=new File("mk01.txt");
		ProblemInputII problemInputII=InitProblemDescription.getProblemDesFromFile(file);
		int proDesMatrix[][]=problemInputII.getProDesMatrix();
		for(int i=0;i<proDesMatrix.length;i++)
		{
			for(int j=0;j<proDesMatrix[0].length;j++)
			{
				System.out.print(proDesMatrix[i][j]+" ");
			}
			System.out.println();
		}
		//InitProblemDescription.localSearch(problemInputII);
		InitProblemDescription.randomSearch(problemInputII);
		//InitProblemDescription.globalSearch(problemInputII);
	}

}
