package com.cxy.util;

import java.util.Random;
import java.util.UUID;

public class CreateCodeUtil {
	public static int getRandomNum() {
		return (int) (Math.random() * 10000);
	}

	public static String getGuid() {
		String guid = UUID.randomUUID().toString();
		return guid.replaceAll("-", "").trim();
	}

	public static String getMililis() {
		String mili = System.currentTimeMillis() + "";
		mili = mili.substring(8);
		return mili;

	}

	public static String getGuidFifteenNum() {
		String guid = createRandom(true, 10) + getMililis();
		return guid.trim();
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	public static void main(String[] args) {
		System.out.println(getCdkey());
		System.out.println(System.currentTimeMillis());

	}

	public static String getCdkey() {
		// 在[23,55）区间内随机取数，注意不包括上界55。
		int[] myRandom = exclusiveRandom(23, 55, 32);// 设定返回值：存放Random类生成的随机数的数组
		String str = "";
		for (int i = 0; i < myRandom.length; i++) {
			str += myRandom[i];
			if (i == 7) {
				break;
			}
		}
		return str.substring(1);
	}
	
	

	public static int[] exclusiveRandom(int from, int to, int time) {

		// 判断实参是否符合随机取值要求

		if (from > to) {// 指定区间下界大于上界，置换
			to = from + to;
			from = to - from;
			to = to - from;
		}

		int range = to - from;

		if (range == 0) {// 取值范围只有一个数
			int[] randomValue = new int[1];
			randomValue[0] = to;
			return randomValue;
		}

		if (time - (range) > 0 || Math.abs(from) != from || Math.abs(to) != to) {
			System.out.println("取值应在正整数范围内，取值个数不应超出给定的范围！");
			int[] randomValue = new int[1];
			randomValue[0] = -1;
			return randomValue;
		}

		int[] randomValue = new int[time];
		int[] count = new int[range];
		int number;// 当前随机数

		// （１）使用Random类生成随机数

		Random random = new Random();

		for (int i = 0; i < time; i++) {// 生成[10,40]范围内的31个无重复随机数
			number = random.nextInt(range) + from; // 随机数取值区间[0,高值减低值）

			if (0 == count[number - from]) {
				randomValue[i] = number;
				count[number - from]++;// 记录次数
			} else
				i--;// 随机数重复，本次取值无效
		}

		for (int i = 0; i < count.length; i++) {
			if (0 == count[i])
				continue;
			count[i] = 0;// 清除记数
		}

		int[] randomValue2 = new int[time];

		for (int i = 0; i < time; i++) {

			// 伪随机生成[0，指定区间上界-下界]的整数，它与标记存在的数组count[]的元素下标对应
			number = (int) (Math.random() * range);

			// 检查当前随机数是否存在，若存在则放弃
			if (0 == count[number]) {
				randomValue2[i] = number + from;// 加上指定区间下界得到区间内随机数
				count[number]++;
			} else
				i--;
		}

		for (int i = 0; i < count.length; i++) {
			if (0 == count[i])
				continue;
			count[i] = 0;// 清除记数
		}

		return randomValue;
	}

	//后一个参数必须大于前一个参数
	public static String getRandom(int length,int pool) {
		  // TODO Auto-generated method stub
		  //从
		  boolean[] array = new boolean[pool];
		  Random r = new Random();
		  for(int i=0;i<length;i++){
		   int j = r.nextInt(pool);
		   if(array[j]){
		    i--;
		    continue;
		   }else{
		    array[j]= true;
		   }
		  }
		  String numstr ="";
		  for(int i=0;i<array.length;i++){
		   if(array[i]){
		     numstr += i;
		    if(numstr.length()>length)
		     break;
		   }
		  }
		  return numstr.substring(0, length);
		 
		 }
}
