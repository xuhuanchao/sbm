package com.xhc.sbm.config.ws;

import com.sun.xml.internal.ws.transport.http.server.EndpointImpl;
import com.xhc.sbm.ws.BallWebService;
import com.xhc.sbm.ws.IBallWebService;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

import javax.xml.ws.Endpoint;

/**
 * @Author: xhc
 * @Date: 2020/7/2 18:06
 * @Description: 使用jdk的@{@link EndpointImpl} 创建web service功能创建服务
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {


    private String port = "8800";

    @Bean
    public Endpoint endpoint(){
        IBallWebService ballWebService = new BallWebService();
        return EndpointImpl.publish(String.format("http://localhost:%s/ball", port), ballWebService);
    }
}
