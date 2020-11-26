package com.cxy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtils {

	public static String decoder(String param) throws UnsupportedEncodingException {
		return param != null ? URLDecoder.decode(param, "UTF-8") : param;
	}
}
