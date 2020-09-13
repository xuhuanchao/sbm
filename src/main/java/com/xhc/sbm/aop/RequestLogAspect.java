package com.xhc.sbm.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * 通过@Aspect 注解实现AOP通知
 * 需要引入aspectj 包
 *
 * @Author: xhc
 * @Date: 2020/3/25 16:57
 * @Description:
 */
//@Aspect
//@Component
@Log4j2
public class RequestLogAspect {


    @Pointcut("execution (* com.xhc.sbm.controller..*.*(..))")
    public void point() {
    }


    @Before("point()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        request.setAttribute("requestTime", System.currentTimeMillis());
        String requestUrl = request.getRequestURL().toString();
        log.info(" ===>接到请求，地址：{}。请求参数：{}", requestUrl, JSON.toJSONString(extractParams(joinPoint)));
    }

    @After("point()")
    public void doAfter(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Long requestTime = (Long)request.getAttribute("requestTime");
        String requestUrl = request.getRequestURL().toString();
        long useTime = System.currentTimeMillis() - requestTime;
        log.info(" ====>请求，{}，消耗时间：{}ms", requestUrl, useTime);
    }

    private List extractParams(JoinPoint joinPoint){
        List list = new ArrayList();
        Object[] args = joinPoint.getArgs();
        if(ArrayUtils.isEmpty(args)){
            return list;
        }else{
            list = Arrays.stream(args)
                    .filter(arg -> (!(arg instanceof ServletRequest)
                            && !(arg instanceof ServletResponse)
                            && !(arg instanceof BindingResult)
                            && !(arg instanceof MultipartFile)
                    ))
                    .collect(Collectors.toList());
        }
        return list;
    }

    /*private Object[] extractParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String s = joinPoint.toString();
        System.out.println(s);
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.asList(args).stream();
        List<Object> logArgs = stream.filter(arg -> (!(arg instanceof ServletRequest)
                && !(arg instanceof ServletResponse)
                && !(arg instanceof MultipartFile))).collect(Collectors.toList());
        if (logArgs.isEmpty()) {
            return null;
        }
        return logArgs.toArray();
    }
*/


//    @Around("point()")
//    public void doAround(JoinPoint joinPoint){
//
//    }


    @AfterThrowing(pointcut = "point()", throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("http请求异常，{}", e.getMessage());
    }
}

