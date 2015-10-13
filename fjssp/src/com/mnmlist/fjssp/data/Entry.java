package com.mnmlist.fjssp.data;
/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 * store the jobNo and the operation count,use for generate one dna
 */
public class Entry
{
	public int index;// 工种号 jobNo
	public int value;// 工序数 procedureNo
	public Entry()
	{
		index = -1;
		value = -1;
	}
}
