package com.xhc.sbm.mq;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Author: xhc
 * @Date: 2020/6/6 17:01
 * @Description:
 */
@Component
@Log4j2
@ConditionalOnProperty(prefix = "app.activeMq.enabled", value = "true", matchIfMissing = false)
public class MqListener {


    @Value("${spring.activemq.queue-name}")
    private String queueName;

    @Value("${spring.activemq.queue-name2}")
    private String queueName2;


    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "jmsListenerContainerQueueFactory")
    @SendTo("queue2")
    public String receiveQueueMsg(String message){
        log.info("收到queue[{}]消息：{}", queueName, message);
        return "消息[" + message + "]，接收成功";
    }

    //topic模式的消费者
    @JmsListener(destination="topicName", containerFactory="jmsListenerContainerTopicFactory")
    public void receiveTopicMsg(String message) {
        log.info("收到topic[{}]消息：{}", queueName, message);
    }

    @JmsListener(destination = "${spring.activemq.queue-name2}")
    public void receiveQueueMsg2(String message){
        log.info("收到queue[{}]消息：{}", queueName2, message);
    }

}
