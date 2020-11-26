package com.cxy.util;

/**
 * 地图
 * @author BZhou
 *
 */
public class MapUtil {

	private static double EARTH_RADIUS = 6371.393;				// 地球半径
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 计算两个经纬度之间的距离(单位：米)
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double latLen = radLat1 - radLat2;
		double lgnLen = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(
				Math.sqrt(
						Math.pow(Math.sin(latLen/2),2)
						+Math.cos(radLat1)
						*Math.cos(radLat2)
						*Math.pow(Math.sin(lgnLen/2),2)
				)
		);  
		s = s * EARTH_RADIUS;  
		s = Math.round(s * 1000);
		return s;
	}
}
