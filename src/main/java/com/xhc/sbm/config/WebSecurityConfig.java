package com.xhc.sbm.config;

import com.xhc.sbm.service.authenticity.CustomerAccessDeniedHandler;
import com.xhc.sbm.service.authenticity.CustomerLogoutSuccessHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 *
 * 安全配置
 *
 * 通过FilterChainProxy 代理Security的所有Filter
 * WebAsyncManagerIntegrationFilter
 * SecurityContextPersistenceFilter
 * HeaderWriterFilter
 * CsrfFilter
 * LogoutFilter
 * UsernamePasswordAuthenticationFilter
 * ConcurrentSessionFilter
 * RequestCacheAwareFilter
 * SecurityContextHolderAwareRequestFilter
 * AnonymousAuthenticationFilter
 * SessionManagementFilter
 * ExceptionTranslationFilter
 * FilterSecurityInterceptor
 *
 *
 *
 *
 * @Author: xhc
 * @Date: 2020/4/9 11:27
 * @Description:
 */
@Configuration
@EnableWebSecurity
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService securityUserDetailsService;

    @Autowired
    private AuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Autowired
    private CustomerLogoutSuccessHandler customerLogoutSuccessHandler;

    @Autowired
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    @Value("${app.security.enabled:false}")
    private boolean useAuthorization = false;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if(useAuthorization){
            http.exceptionHandling().accessDeniedHandler(customerAccessDeniedHandler);
            http.authorizeRequests()
                    .antMatchers("/resources/**", "/favicon.ico", "/signup", "/about").permitAll()
                    .antMatchers("/admin/**").access("hasRole('admin')")  //.hasRole("admin") 使用hasRole() 方法会给角色添加ROLE_ 前缀
                    .antMatchers("/db/**").access("hasRole('admin') and hasRole('dba')")
                    .anyRequest().authenticated()
                    .and()
                    .csrf().disable()  //禁用csrf，默认启用
//                .and()
                    .authenticationProvider(daoAuthenticationProvider())
                    .formLogin()
//                .failureHandler(customerAuthenticationFailureHandler)  //使用指定的验证失败处理
                    .loginPage("/goLogin")
                    .loginProcessingUrl("/user/login")  //默认使用 /login 做登录处理
                    .permitAll()
//                .successForwardUrl("/index.html")   //登录成功后重定向
                    .defaultSuccessUrl("/", false) //登录成功后访问原请求地址，true时和successForwardUrl一样都访问指定地址
                    .and()
//                .formLogin().and().httpBasic().and()
                    .logout()
                    .logoutUrl("/user/logout")//默认使用 logout做登出处理
                    .permitAll()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(customerLogoutSuccessHandler) //登出成功后执行操作
                    .invalidateHttpSession(true)

                    .and()
                    .sessionManagement()
                    .maximumSessions(2); //一个用户同时登录的个人


//                .and()        //login设置  自定义登录页面且允许所有用户登录
//                .formLogin()
//                .loginPage("/login") //The updated configuration specifies the location of the log in page  指定自定义登录页面
//                .permitAll() // 允许所有用户访问登录页面. The formLogin().permitAll() 方法
//                .and()
//                .logout()  //logouts 设置
//                .logoutUrl("/my/logout")  // 指定注销路径
//                .logoutSuccessUrl("/my/index") //指定成功注销后跳转到指定的页面
//                .logoutSuccessHandler(logoutSuccessHandler)  //指定成功注销后处理类 如果使用了logoutSuccessHandler()的话， logoutSuccessUrl()就会失效
//                .invalidateHttpSession(true)  // httpSession是否有效时间，如果使用了 SecurityContextLogoutHandler，其将被覆盖
//                .addLogoutHandler(logoutHandler)  //在最后增加默认的注销处理类LogoutHandler
//                .deleteCookies(cookieNamesToClear)//指定注销成功后remove cookies
//                //增加在FilterSecurityInterceptor前添加自定义的myFilterSecurityInterceptor
//                http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);

        }else {
            //放开所有权限控制，为了使用spring boot admin 注册到server时，server可以获取服务监控信息
            http.csrf().disable()
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and().logout().permitAll();
        }


    }



    /**
     * 自定义{@link org.springframework.security.access.expression.SecurityExpressionRoot#hasAnyRole(String...)}
     * 做角色判断时，给角色前添加前缀，默认添加ROLE_
     *
     * 使用{@link ExpressionUrlAuthorizationConfigurer.AuthorizedUrl#access(String)} 添加访问权限时 没有添加ROLE_前缀
     * @return
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false); //去掉对UsernameNotFoundException 异常的屏蔽
        daoAuthenticationProvider.setUserDetailsService(securityUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//        daoAuthenticationProvider.setAuthoritiesMapper();
        return daoAuthenticationProvider;
    }



}
