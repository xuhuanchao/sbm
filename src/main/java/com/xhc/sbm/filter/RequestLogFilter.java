package com.xhc.sbm.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


/**
 * @Author: xhc
 * @Date: 2020/4/13 15:52
 * @Description:
 */
@WebFilter
@Component("logFilter")
@Log4j2
public class RequestLogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        request.setAttribute("requestTime", System.currentTimeMillis());
        String requestUrl = request.getRequestURL().toString();

        String host = request.getHeader("host");   // nginx中添加的
        String realIp = request.getHeader("X-Real-IP");   // nginx中添加的

        StringBuilder allHeaders = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerKey = headerNames.nextElement();
            String headerValue = request.getHeader(headerKey);
            allHeaders.append(String.format("%s = %s, ", headerKey, headerValue));
        }

        LogHttpServletRequest wrappedRequest = new LogHttpServletRequest(request);
        log.info(" ===>接到请求，地址:{}，header=[{}]。请求内容：{}", requestUrl, allHeaders.toString(), IOUtils.toString(wrappedRequest.getBody(), "utf-8"));

//        filterChain.doFilter(wrappedRequest, servletResponse);

        LogHttpServletResponse wrappedResponse = new LogHttpServletResponse((HttpServletResponse) servletResponse);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        //略过swagger请求
        if(requestUrl.contains("/api/webjars")){
            return;
        }
        log.info(" ===>接到请求，地址:{}，header=[host:{}，X-Real-IP:{} ]。响应内容：{}", requestUrl, host, realIp, wrappedResponse.getResponseBody());

    }




}
