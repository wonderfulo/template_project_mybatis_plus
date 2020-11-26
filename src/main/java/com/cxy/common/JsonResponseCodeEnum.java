package com.cxy.common;

/**
 * json返回编码枚举
 *
 */
public enum JsonResponseCodeEnum {

	/**
	 * 成功code
	 */
	SUCCESS(102, "成功"),

	/**
	 * 失败code
	 */
	FAILED(101, "失败"),

    /**
     * 失败code
     */
    INVAILD(1001, "token失效");

	/**
	 * 返回编码
	 */
	private int code;

	/**
	 * 默认返回信息
	 */
	private String msg;

	JsonResponseCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
