package com.cxy.exception;

import com.cxy.common.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    public JsonResponse<String> defaultExceptionHandler(HttpServletRequest request, Exception e){
        if (e instanceof MyException){
            System.out.println(e);
            return JsonResponse.fail(e.getMessage());
        }else{
            e.printStackTrace();
        }
        return null;
    }
}
