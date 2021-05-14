package com.cxy.common;

import com.cxy.constant.JsonResponseCodeEnum;

import java.text.SimpleDateFormat;

/**
 * json格式数据返回对象
 * 必须有get和set方法，或者使用public来修饰属性变量，否则fastjson无法获取到值
 */
public class JsonResponse<T> {

    private int code;

    private String msg;

    private T data;

    private String serverTime;

    public JsonResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.serverTime = dateFormat.format(System.currentTimeMillis());
    }

    /**
     * 请求失败，使用默认错误信息
     *
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> invalid() {
        return fail(JsonResponseCodeEnum.INVAILD.getMsg());
    }

    /**
     * 请求失败
     *
     * @param msg 自定义错误信息
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> invalid(String msg) {
        return new JsonResponse<>(JsonResponseCodeEnum.INVAILD.getCode(), msg, null);
    }

    /**
     * 请求成功
     *
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> success() {
        return success(null);
    }

    /**
     * 请求成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>(JsonResponseCodeEnum.SUCCESS.getCode(),
                JsonResponseCodeEnum.SUCCESS.getMsg(),
                data);
    }

    /**
     * 请求失败，使用默认错误信息
     *
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> fail() {
        return fail(JsonResponseCodeEnum.FAILED.getMsg());
    }

    /**
     * 请求失败
     *
     * @param msg 自定义错误信息
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> fail(String msg) {
        return new JsonResponse<T>(JsonResponseCodeEnum.FAILED.getCode(), msg, null);
    }

    /**
     * 请求失败
     *
     * @param msg 自定义错误信息
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> fail(int code, String msg) {
        return new JsonResponse<T>(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
