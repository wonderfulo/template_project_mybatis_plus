package com.cxy.exception;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName 我的异常
 * @Description
 * @createTime 2021年05月14日 09:38:00
 */
public class MyException extends GeneralException {

    private static final long serialVersionUID = 4061103558731508181L;

    public MyException(int code) {
        super(code);
    }

    public MyException(String msg) {
        super(msg);
    }

    public MyException(int code, String msg) {
        super(code, msg);
    }
}
