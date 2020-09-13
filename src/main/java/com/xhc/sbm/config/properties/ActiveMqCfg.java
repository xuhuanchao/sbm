package com.xhc.sbm.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: xhc
 * @Date: 2020/4/30 11:15
 * @Description:
 */
@Data
@Component
@ConfigurationProperties("spring.activemq")
public class ActiveMqCfg {

    private String queueName;

    private String topicName;

}
