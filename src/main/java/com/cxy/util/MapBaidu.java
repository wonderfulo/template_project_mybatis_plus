//package com.cxy.util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//public class MapBaidu {
//
//	private static final String AK = "TZZgAp6rh0maWqcaRNb1xOzhbgePKczx";
//
//	private static final String DOMAIN = "http://api.map.baidu.com";
//
//	@SuppressWarnings("static-access")
//	public static void main(String[] args) throws IOException {
//		System.out.println("防守对方".substring(0, 2));
//		MapBaidu getLatAndLngByBaidu = new MapBaidu();
//
//		Map<String, Double> map = getLatAndLngByBaidu.getLngAndLat2("广东深圳龙岗区坂田五和大道五和综合市场阳光湾畔百货商场一楼1010铺位场阳光");
//		System.out.println("经度：" + map.get("lng") + "---纬度：" + map.get("lat"));
//	}
//
//	/**
//	 * 根据百度地图获取地址的经纬度
//	 * @param address
//	 * @return {经度:lng, 纬度:lat}
//	 */
//	public static Map<String, Double> getLngAndLat2(String address) {
//		Map<String, Double> map = new HashMap<String, Double>();
//		String addre = "";
//		if(address.length() > 50){
//			addre = address.trim().substring(0,50);
//		}else{
//			addre = address;
//		}
//		Map<String, Object> param = new HashMap<>();
//		param.put("address", addre);
//		param.put("output", "json");
//		param.put("ak", AK);
//
//		try {
//			String json = HttpJsonApiUtils.readContentFromGet(DOMAIN+"/geocoder/v2/", param);
//			JSONObject obj = JSONObject.fromObject(json);
//			if (obj.get("status").toString().equals("0")) {
//				double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
//				double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
//				map.put("lng", lng);
//				map.put("lat", lat);
//			} else {
//				map.put("lng", 9999d);
//				map.put("lat", 9999d);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}
//
//	/**
//	 * 获取经纬度
//	 * @param address
//	 * @param ak=RRGgKhHL9GjMAvaiigtlrmFSHi31eZDL,app=chaojidaogou,
//	 * @return
//	 */
//	public static Map<String, Double> getLngAndLat(String address) {
//		Map<String, Double> map = new HashMap<String, Double>();
//		String addre = "";
//		if(address.length() > 50){
//			addre = address.trim().substring(0,50);
//		}else{
//			addre = address;
//		}
//		String url = "http://api.map.baidu.com/geocoder/v2/?address=" + addre.trim()
//				+ "&output=json&ak=RRGgKhHL9GjMAvaiigtlrmFSHi31eZDL";
//		String json = loadJSON(url);
//		JSONObject obj = JSONObject.fromObject(json);
//		if (obj.get("status").toString().equals("0")) {
//			double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
//			double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
//			map.put("lng", lng);
//			map.put("lat", lat);
//			// System.out.println("经度："+lng+"---纬度："+lat);
//		} else {
//			// System.out.println("未找到相匹配的经纬度！");
//		}
//		return map;
//	}
//
//	public static String loadJSON(String url) {
//		StringBuilder json = new StringBuilder();
//		try {
//			URL oracle = new URL(url);
//			URLConnection yc = oracle.openConnection();
//			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//			String inputLine = null;
//			while ((inputLine = in.readLine()) != null) {
//				json.append(inputLine);
//			}
//			in.close();
//		} catch (MalformedURLException e) {
//		} catch (IOException e) {
//		}
//		return json.toString();
//	}
//
//
//	public static Map<String, String> getLatitude(String address) {
//		try {
//			// 将地址转换成utf-8的16进制
//			address = URLEncoder.encode(address, "UTF-8");
//			// 如果有代理，要设置代理，没代理可注释
//			// System.setProperty("http.proxyHost","192.168.172.23");
//			// System.setProperty("http.proxyPort","3209");
//
//			URL resjson = new URL("http://api.map.baidu.com/geocoder?address="
//					+ address + "&output=json&key=RRGgKhHL9GjMAvaiigtlrmFSHi31eZDL");
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					resjson.openStream()));
//			String res;
//			StringBuilder sb = new StringBuilder("");
//			while ((res = in.readLine()) != null) {
//				sb.append(res.trim());
//			}
//			in.close();
//			String str = sb.toString();
//			System.out.println("return json:" + str);
//			if(str!=null&&!str.equals("")){
//				Map<String, String> map = null;
//				int lngStart = str.indexOf("lng\":");
//				int lngEnd = str.indexOf(",\"lat");
//				int latEnd = str.indexOf("},\"precise");
//				if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
//					String lng = str.substring(lngStart + 5, lngEnd);
//					String lat = str.substring(lngEnd + 7, latEnd);
//					map = new HashMap<String, String>();
//					map.put("lng", lng);
//					map.put("lat", lat);
//					return map;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//}