package com.cxy.constant.exception;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @Description 我的枚举异常
 * @createTime 2021年05月11日 11:31:00
 */
public enum MyEnumException implements IEnumException {

    ACCESS_TOKEN_NO_EXIST(null, "accessToken不存在！"),
    ;


    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    MyEnumException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
