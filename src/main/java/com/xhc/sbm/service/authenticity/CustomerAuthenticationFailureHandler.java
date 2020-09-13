package com.xhc.sbm.service.authenticity;

import com.alibaba.fastjson.JSON;
import com.xhc.sbm.bean.ResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 使用自定义验证失败处理方法，但是不能将信息返回至页面！！！
 *
 *
 * @Author: xhc
 * @Date: 2020/4/13 23:48
 * @Description:
 */
@Component
public class CustomerAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message = e.getMessage();
        ResponseResult result = ResponseResult.error(0, message);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));

    }
}
