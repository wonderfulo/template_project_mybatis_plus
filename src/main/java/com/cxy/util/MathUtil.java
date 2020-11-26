package com.cxy.util;

import java.math.BigDecimal;

public class MathUtil {

	private static final int DIV_SCALE = 2;
	/**
	 * 精确的加法运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static <T> BigDecimal add(T v1, T v2) {
		BigDecimal bd1 = new BigDecimal(v1.toString());
		BigDecimal bd2 = new BigDecimal(v2.toString());
		return bd1.add(bd2);
	}
	
	/**
	 * 精确的减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return
	 */
	public static <T> BigDecimal sub(T v1, T v2) {
		BigDecimal bd1 = new BigDecimal(v1.toString());
		BigDecimal bd2 = new BigDecimal(v2.toString());
		return bd1.subtract(bd2);
	}
	
	/**
	 * 精确的乘法运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static <T> BigDecimal mul(T v1, T v2) {
		BigDecimal bd1 = new BigDecimal(v1.toString());
		BigDecimal bd2 = new BigDecimal(v2.toString());
		return bd1.multiply(bd2);
	}
	
	/**
	 * 提供（相对）精确的除法运算，由scale参数指 定精度四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale
	 * @return
	 */
	public static <T> BigDecimal div(T v1, T v2, int scale) {
		BigDecimal bd1 = new BigDecimal(v1.toString());
		BigDecimal bd2 = new BigDecimal(v2.toString());
		return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 提供（相对）精确的除法运算，由scale参数指 定精度四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @return
	 */
	public static <T> BigDecimal div(T v1, T v2) {
		BigDecimal bd1 = new BigDecimal(v1.toString());
		BigDecimal bd2 = new BigDecimal(v2.toString());
		return bd1.divide(bd2, DIV_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 提供精确的小数位四舍五入处理
	 * @param v
	 * @param scale
	 * @return
	 */
	public static BigDecimal round(double v, int scale) {
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal(1);
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 提供精确的小数位(舍弃后面的)
	 * @param v
	 * @param scale
	 * @return
	 */
	public static BigDecimal roundDown(double v, int scale) {
		/*String format = "#.";
		for(int i=0; i<scale; i++) {
			format+="0";
		}
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.DOWN);
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		return new BigDecimal(df.format(v));*/
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal(1);
		return b.divide(one, scale, BigDecimal.ROUND_DOWN);
	}
	
	public static void main(String[] args) {
		System.out.println(MathUtil.mul("-1", 100));
	}
}
