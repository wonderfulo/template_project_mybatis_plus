package com.cxy.exception;

import com.cxy.utils.msg.MsgUtil;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName GeneralException.java
 * @Description 一般异常
 * @createTime 2021年05月14日 09:40:00
 */
public class GeneralException extends RuntimeException {

    private static final long serialVersionUID = -669975469118226899L;
    private String msg;
    private int code;

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    public GeneralException() {
        super(MsgUtil.getMessageByCode("global.error"));
    }


    public GeneralException(int code) {
        this.code = code;
        this.msg = MsgUtil.getExceptionMsgByCode(code + "");
    }

    public GeneralException(String msg) {
        this.msg = msg;
    }

    public GeneralException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GeneralException(Throwable throwable) {
        super(throwable);
        this.msg = MsgUtil.getMessageByCode(throwable.getMessage());
    }

    public GeneralException(String code, Throwable throwable) {
        super(throwable);
        this.msg = MsgUtil.getMessageByCode(code);
    }

}
