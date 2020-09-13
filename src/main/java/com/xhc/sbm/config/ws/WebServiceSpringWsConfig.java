package com.xhc.sbm.config.ws;

//import org.apache.cxf.Bus;
//import org.apache.cxf.bus.spring.SpringBus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
//import org.springframework.ws.transport.http.MessageDispatcherServlet;


/**
 * @Author: xhc
 * @Date: 2020/6/30 11:35
 * @Description: 使用spring-boot-starter-web-services 模块创建服务
 */
@EnableWs
@SpringBootConfiguration
public class WebServiceSpringWsConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/ws/*");
        return servletRegistrationBean;
    }



}
