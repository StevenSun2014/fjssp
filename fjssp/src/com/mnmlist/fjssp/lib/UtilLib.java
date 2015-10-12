package com.mnmlist.fjssp.lib;

import com.mnmlist.fjssp.data.ProblemInfo;

/**
 * @author mnmlist@163.com
 * @blog http://blog.csdn.net/mnmlist
 * @version v1.0
 */
public class UtilLib {
	public static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	/**
	 * @param para the inputparameters
	 * @param k the iteration number
	 * @param startTime
	 * @return
	 */
	public static boolean isEnd(ProblemInfo input, int k, long startTime) {
		int timeLimit=input.getTimeLimit();
		if (input.getIteratorCount() > 0) {
			if (k >= input.getIteratorCount()) {
				return true;
			}
		}
		if (timeLimit > 0) {
			long t = System.currentTimeMillis();
			if ((t - startTime) > timeLimit) {
				return true;
			}
		}
		return false;
	}
}
