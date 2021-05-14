package com.cxy.constant.exception;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName DisplayEnumException.java
 * @Description 陈列枚举异常
 * @createTime 2021年05月11日 11:31:00
 */
public enum DisplayTaskEnumException implements IEnumException {

    ID_NOT_EXIST(null, "displayTaskId不存在！"),

    ;


    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    DisplayTaskEnumException(Integer code, String message) {
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
