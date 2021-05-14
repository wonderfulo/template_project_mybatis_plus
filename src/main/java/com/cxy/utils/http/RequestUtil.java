package com.cxy.utils.http;

import com.cxy.utils.bean.AppUtil;
import org.springframework.core.env.Environment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"})
public class RequestUtil {

	public static Object request(String address, String method, Map args) throws Exception {
		BufferedReader reader;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.connect();

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			// request arguments
			String param = genParams(args);
			out.writeBytes(param);
			out.flush();
			out.close();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String lines = "";
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes("utf-8"), "utf-8");
				sb.append(lines);
			}
			reader.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * 模拟http的get请求
	 * @param url 请求地址
	 * @param param 参数
	 * @return
	 * @throws Exception
	 */
	public static String readContentFromGet(String url, Map param) throws Exception {
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String getURL = url+"?"+MapToString(param);
		URL getUrl = new URL(getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		// 建立与服务器的连接，并未发送数据
		connection.setRequestProperty("Accept", "application/json");
		connection.connect();
		connection.setConnectTimeout(3000);
		connection.setReadTimeout(3000);

		// 发送数据到服务器并使用Reader读取返回的数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
		StringBuffer lineArr = new StringBuffer("");
		String lines = "";
		while ((lines = reader.readLine()) != null) {
			lineArr.append(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return lineArr.toString();
	}

	public static String MapToString(Map param) throws Exception{
		StringBuffer outStr = new StringBuffer("");
		if(param!=null && param.size()>0)
		{
			int i = 0;
			Set outSet = param.keySet();
			Iterator ite = outSet.iterator();
			while (ite.hasNext()) {
				String key = ite.next().toString();
				String value = param.get(key)!=null?param.get(key).toString():null;
				if(value != null){
					value = URLEncoder.encode(value, "utf-8");
					if(i==0)
						outStr.append(key+"="+value);
					else
						outStr.append("&"+key+"="+value);
					i++;
				}
			}
		}
		return outStr.toString();
	}

	private static String genParams(Map args) throws UnsupportedEncodingException {
		StringBuffer buf = new StringBuffer();
		Iterator it = args.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			buf.append(key);
			buf.append("=");
			if (args.get(key) != null) {
				buf.append(URLEncoder.encode(String.valueOf(args.get(key)), "UTF-8"));
			} else {
				buf.append("");
			}
			buf.append("&");
		}
		return buf.toString();
	}

	/**
	 * 获取接口调用IP地址
	 *
	 * @return
	 * @throws IOException
	 */
	public static String getDoMain() {
		//从环境变量中获取参数
		Environment env = (Environment) AppUtil.getCtx().getBean("environment");
		String url = env.getProperty("shopguideSiteDomain");
		return url;
	}
}
