package com.cxy.exception;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName 服务异常
 * @Description
 * @createTime 2021年05月14日 09:38:00
 */
public class ServiceException extends GeneralException {

    private static final long serialVersionUID = 4061103558721508181L;

    public ServiceException(int code) {
        super(code);
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(int code, String msg) {
        super(code, msg);
    }
}
