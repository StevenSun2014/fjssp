package com.mnmlist.fjssp.data;
/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 * store the start time and the end time of some operationNo of some job
 */
public class Operation
{
	public int jobNo;
	public int operationNo;
	public int startTime;
	public int endTime;

	public Operation()
	{
		jobNo = -1;
		operationNo = -1;
		startTime = -1;
		endTime = -1;
	}
	public void initOperation()
	{
		jobNo = -1;
		operationNo = -1;
		startTime = -1;
		endTime = -1;
	}
}
