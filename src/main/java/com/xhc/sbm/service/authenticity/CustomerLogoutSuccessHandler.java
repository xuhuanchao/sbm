package com.xhc.sbm.service.authenticity;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出操作
 *
 * @Author: xhc
 * @Date: 2020/4/14 12:10
 * @Description:
 */
@Log4j2
@Component
public class CustomerLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        log.info("==> 用户:" + user.getUsername() + " 退出登录！");
        response.sendRedirect("/");
    }
}
