package com.cxy.interceptor;

import com.cxy.common.JsonResponse;
import com.cxy.exception.MyException;
import com.cxy.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * @author ：陈翔宇
 * @date ：2020/12/2 9:06
 * @description：全局异常处理类
 * @modified By：
 * @version: $
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> defaultExceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return JsonResponse.fail("服务器开小差，请联系管理员");
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public JsonResponse<Object> defaultExceptionHandler(HttpServletRequest request, MyException e) {
        System.out.println(e);
        return Optional.ofNullable(e)
                .filter(e1 -> e1.getCode() != 0)
                .map(code -> JsonResponse.fail(code.getCode(), code.getMsg()))
                .orElse(JsonResponse.fail(e.getMsg()));
    }


    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public JsonResponse<String> defaultExceptionHandler(HttpServletRequest request, ServiceException e) {
        System.out.println(e);
        if (e.getCode() != 0) {
            return JsonResponse.fail(e.getCode(), e.getMsg());
        }
        return JsonResponse.fail(e.getMsg());
    }
}
