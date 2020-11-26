//package com.cxy.util;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.struts2.json.JSONUtil;
//
//import com.vsvz.utils.json.JsonUtil;
//
//import net.sf.json.JSONObject;
//
//public class HttpJsonApiUtils {
//	static Log log = LogFactory.getLog(HttpJsonApiUtils.class);
//	public static Object request(String address, String method, Map args){
//		BufferedReader reader = null;
//		HttpURLConnection conn = null;
//		try {
//			URL url = new URL(address);
//			conn = (HttpURLConnection)url.openConnection();
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setRequestMethod(method);
//			conn.setUseCaches(false);
//			conn.setInstanceFollowRedirects(true);
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//			conn.connect();
//			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//			//request arguments
//			String param = genParams(args);
//			out.writeBytes(param);
//            out.flush();
//            out.close();
//
//			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//			StringBuffer sb = new StringBuffer("");
//			String lines = "";
//            while ((lines = reader.readLine()) != null) {
//                lines = new String(lines.getBytes("utf-8"), "utf-8");
//                sb.append(lines);
//            }
//            reader.close();
//            return JSONUtil.deserialize(sb.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			conn.disconnect();
//			conn = null;
//		}
//		return null;
//	}
//
//	public static Object requestJson(String address, String method, Map args){
//		BufferedReader reader = null;
//		HttpURLConnection conn = null;
//		try {
//			URL url = new URL(address);
//			conn = (HttpURLConnection)url.openConnection();
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setRequestMethod(method);
//			conn.setUseCaches(false);
//			conn.setInstanceFollowRedirects(true);
//			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//			conn.connect();
//			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//			//request arguments
//			String param = genJsonParams(args);
//			out.writeBytes(param);
//            out.flush();
//            out.close();
//
//			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//			StringBuffer sb = new StringBuffer("");
//			String lines = "";
//            while ((lines = reader.readLine()) != null) {
//                lines = new String(lines.getBytes("utf-8"), "utf-8");
//                sb.append(lines);
//            }
//            reader.close();
//            return JSONUtil.deserialize(sb.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			conn.disconnect();
//			conn = null;
//		}
//		return null;
//	}
//
//	/**
//	 * 模拟http的get请求
//	 * @param url 请求地址
//	 * @param param 参数
//	 * @return
//	 * @throws IOException
//	 */
//	public static String readContentFromGet(String url, Map param) throws IOException {
//		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
//		String getURL = url+"?"+MapToString(param);
//		URL getUrl = new URL(getURL);
//		// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
//		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
//		// 建立与服务器的连接，并未发送数据
//		connection.setRequestProperty("Accept", "application/json");
//		connection.connect();
//		// 发送数据到服务器并使用Reader读取返回的数据
//		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//		StringBuffer lineArr = new StringBuffer("");
//		String lines = "";
//        while ((lines = reader.readLine()) != null) {
//        	lineArr.append(lines);
//        }
//        reader.close();
//        // 断开连接
//        connection.disconnect();
//        return lineArr.toString();
//	}
//
//	public static String MapToString(Map param) throws IOException{
//		StringBuffer outStr = new StringBuffer("");
//		if(param!=null && param.size()>0)
//		{
//			int i = 0;
//			Set outSet = param.keySet();
//			Iterator ite = outSet.iterator();
//			while (ite.hasNext()) {
//				String key = ite.next().toString();
//				String value = param.get(key)!=null?param.get(key).toString():null;
//				if(value != null){
//					value = URLEncoder.encode(value, "utf-8");
//					if(i==0)
//						outStr.append(key+"="+value);
//					else
//						outStr.append("&"+key+"="+value);
//					i++;
//				}
//			}
//		}
//		return outStr.toString();
//	}
//
//	/**
//	 * 参数字串
//	 * @param args
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	private static String genParams(Map args) throws UnsupportedEncodingException{
//		StringBuffer buf = new StringBuffer();
//		Iterator it = args.keySet().iterator();
//		try {
//			while (it.hasNext()) {
//				String key = (String) it.next();
//				buf.append(key);
//				buf.append("=");
//				buf.append(args.get(key)!=null?URLEncoder.encode(args.get(key).toString(),"UTF-8"):null);
//				buf.append("&");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return buf.toString();
//	}
//
//	/**
//	 * 生产json参数字串
//	 * @param args
//	 * @return
//	 */
//	public static String genJsonParams(Map args) {
//		JSONObject obj = new JSONObject();
//		Iterator it = args.keySet().iterator();
//		while (it.hasNext()) {
//			String key = (String) it.next();
//			obj.element(key, args.get(key));
//		}
//		return obj.toString();
//	}
//
//	public static String genDisgest(String ak,String sk){
//		Random ra = new Random();
//		String timestamp = String.valueOf(System.currentTimeMillis()) + String.valueOf(ra.nextInt(1000));
//		String hash = ak + "." + timestamp + "." + sk;
//		String digest = ak + "." + timestamp  + "." + DigestUtils.getMD5(hash);
//		return digest;
//	}
//
//    /**
//     * 发送HttpPost请求
//     *
//     * @param strURL
//     *            服务地址
//     * @param params
//     *
//     * @return 成功:返回json字符串<br/>
//     */
//    public static String jsonPost(String strURL, Map  params) {
//        try {
//            URL url = new URL(strURL);// 创建连接
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setUseCaches(false);
//            connection.setInstanceFollowRedirects(true);
//            connection.setRequestMethod("POST"); // 设置请求方式
//            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
//            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
//            connection.connect();
//            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
//            out.append(JsonUtil.toJSONStringObj(params));
//            out.flush();
//            out.close();
//
//            int code = connection.getResponseCode();
//            InputStream is = null;
//            if (code == 200) {
//                is = connection.getInputStream();
//            } else {
//                is = connection.getErrorStream();
//            }
//
//            // 读取响应
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                String result = new String(data, "UTF-8"); // utf-8编码
//                return result;
//            }
//
//        } catch ( Exception e) {
//            e.printStackTrace();
//        }
//        return "error"; // 自定义错误信息
//    }
//
//    /**
//     * 顾家同步post请求方法，因为他们没有任何返回值，所以要重新定义一个方法
//     * @param address
//     * @param args
//     */
//	public static void requestKuka(String address, Map args){
//		BufferedReader reader = null;
//		HttpURLConnection conn = null;
//		try {
//			URL url = new URL(address);
//			conn = (HttpURLConnection)url.openConnection();
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setRequestMethod("POST");
//			conn.setUseCaches(false);
//			conn.setInstanceFollowRedirects(true);
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//			conn.connect();
//			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//			String param = genParams(args);
//			out.writeBytes(param);
//            out.flush();
//            out.close();
//
//			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//			StringBuffer sb = new StringBuffer("");
//			String lines = "";
//            while ((lines = reader.readLine()) != null) {
//                lines = new String(lines.getBytes("utf-8"), "utf-8");
//                sb.append(lines);
//            }
//            reader.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			conn.disconnect();
//			conn = null;
//		}
//	}
//
//	public static void main(String[] args){
//		/*log.debug("start...");
//		String ak = "test";
//		String sk = "thisisatesttestisthis";
//		System.out.println(HttpJsonApiUtils.genDisgest(ak,sk));*/
//		Map map = new HashMap();
//		map.put("loginName", "loginName");
//		map.put("password", "password");
//		map.put("name", "name");
//		map.put("tel", "13000000000");
//		map.put("storeId", "100");
//		map.put("appId", "csb");
//		map.put("userCode", "userCode");
//		map.put("gender", 0);
//
//		String workFlag = "0";
//		// workFlag = workFlag.equals("1") ? "0" : "1";// 超导0:离职,1：在职，会员
//															// 0在职，1离职，所以要调整
//		map.put("workFlag", workFlag);
//		map.put("headPic", "");
//		map.put("userPost", "2");
//		map.put("createTime", DateUtil.formatDateTimeTwo(new Date()));
//		String ret = jsonPost("http://test.mitangtang.cn/member/data_syn/guide",map);
//		System.out.println(ret);
////		String address = "http://api.cutt.com/auth/authentication";
////		String method = "POST";
////		Map params = new HashMap();
////		params.put("email", "1799144790@qq.com");
////		params.put("password", "654321");
////		params.put("digest", genDisgest(ak,sk));
////		try {
////			Object obj = HttpJsonApiUtils.request(address, method, params);
////			String success = BeanUtils.getProperty(obj, "success");
////			String token = BeanUtils.getProperty(obj, "token");
////			String appId = BeanUtils.getProperty(obj, "appId");
////			String apps = BeanUtils.getProperty(obj, "apps");
////			String message = BeanUtils.getProperty(obj, "message");
////			System.out.println("success=" + success);
////			System.out.println("token=" + token);
////			System.out.println("appId=" + appId);
////			System.out.println("apps=" + apps);
////			System.out.println("message=" + message);
////		} catch (IllegalAccessException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (InvocationTargetException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (NoSuchMethodException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	}
//}
