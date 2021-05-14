package com.cxy.constant;

/**
 * json返回编码枚举
 *
 * @author lichaofeng
 * @date 2019/09/05
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
	private final int code;

	/**
	 * 默认返回信息
	 */
	private final String msg;

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
