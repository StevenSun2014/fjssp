package com.mnmlist.jsp;

import java.util.Random;

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
	public static boolean isEnd(ParameterInput para, int k, long startTime) {
		if (para.iterationLimit > 0) {
			if (k >= para.iterationLimit) {
				return true;
			}
		}
		if (para.timeLimit > 0) {
			long t = System.currentTimeMillis();
			if ((t - startTime) > para.timeLimit) {
				return true;
			}
		}
		return false;
	}
}
