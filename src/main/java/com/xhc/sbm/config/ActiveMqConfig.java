package com.xhc.sbm.config;

import com.xhc.sbm.config.properties.ActiveMqCfg;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @Author: xhc
 * @Date: 2020/4/30 10:55
 * @Description:
 */
@Configuration
@EnableJms
@ConditionalOnProperty(prefix = "app.activeMq.enabled", value = "true", matchIfMissing = false)
public class ActiveMqConfig {

    /**
     * topic模式 listener
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopicFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    /**
     * queue模式 listener
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueueFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public Queue queue(ActiveMqCfg activeMqCfg){
        return new ActiveMQQueue(activeMqCfg.getQueueName());
    }

    @Bean
    public Topic topic(ActiveMqCfg activeMqCfg){
        return new ActiveMQTopic(activeMqCfg.getTopicName());
    }

}
