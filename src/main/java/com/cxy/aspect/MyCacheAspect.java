package com.cxy.aspect;

import com.cxy.annonation.MyCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 自定义注解切面
 * </p>
 *
 * @author 陈翔宇
 * @since 2021-1-2
 */
@Aspect
@Component
@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行, 当然也可以不写这注解, 对于写和不写@order的两个切面, 有@order的优先于无@order的执行; 都有@order时, 越小越执先执行
@SuppressWarnings({"unused"})
public class MyCacheAspect {

    public static final Logger logger = LoggerFactory.getLogger(MyCacheAspect.class);

    public static final String TOKEN_KEY = "token";

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.cxy.annonation.MyCache)")
    public void annotationPointcut() {

    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    	// 此处进入到方法前  可以实现一些业务逻辑
    }

    @Around(value = "@annotation(myCache)")
    public Object doAround(ProceedingJoinPoint joinPoint, MyCache myCache) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取参数名称
        String[] params = methodSignature.getParameterNames();
        // 获取参数值
        Object[] args = joinPoint.getArgs();
        String key = myCache.key();

        logger.debug("正在执行自定义注解的切面方法");

        Object o = redisTemplate.opsForValue().get(key);
        if (o != null){
            return o;
        }
        Object proceed = joinPoint.proceed();
        //设置超时时间1小时
        redisTemplate.opsForValue().set(key,proceed,1, TimeUnit.HOURS);
        return proceed;
    }

    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     * @param joinPoint
     */
    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }


}