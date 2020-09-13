package com.xhc.sbm.controller;

import com.xhc.sbm.mq.MqListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @Author: xhc
 * @Date: 2020/6/6 16:53
 * @Description:
 */
@RestController
@RequestMapping(value = "mq")
@Api(tags = "MQ 测试")
@ConditionalOnProperty(prefix = "app.activeMq.enabled", value = "true", matchIfMissing = false)
public class MqTestController  {

    @Autowired
    private JmsMessagingTemplate jms;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Autowired
    private MqListener mqListener;

    @ApiOperation(value = "发送文本消息", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "消息内容", defaultValue = "", required = true),
            @ApiImplicitParam(name = "type", value = "1:发送到queue，其他发送到topic", defaultValue = "", required = true)
    })
    @RequestMapping(value = "/sendMsg")
    public String sendMsg(String msg, String type){
        if("1".equals(type)){
            jms.convertAndSend(queue, msg);
        }else{
            jms.convertAndSend(topic, msg);
        }
        return "发送成功";
    }
}
