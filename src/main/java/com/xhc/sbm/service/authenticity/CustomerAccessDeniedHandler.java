package com.xhc.sbm.service.authenticity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义没有权限拒绝访问处理
 * @Author: xhc
 * @Date: 2020/4/24 17:57
 * @Description:
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (isAjaxRequest(request)) {// AJAX请求,使用response发送403
            response.sendError(403);
        } else if (!response.isCommitted()) {// 非AJAX请求，跳转系统默认的403错误界面，在web.xml中配置
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    accessDeniedException.getMessage());
        }
    }


    /**
     *  判断是否为ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals(
                "XMLHttpRequest"))) {
            return true;
        }
        return false;
    }
}
