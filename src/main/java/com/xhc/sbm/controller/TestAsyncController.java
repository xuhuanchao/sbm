package com.xhc.sbm.controller;

import com.xhc.sbm.service.TestAsyncService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "测试spring boot 异步线程池")
@RequestMapping("async")
public class TestAsyncController {

    @Autowired
    private TestAsyncService testAsyncService;

    @RequestMapping("/work")
    public Map work() {
        Map<Object, Object> map = new HashMap<>();
        map.put("result", "success");
        testAsyncService.doWork();
        return map;
    }


}
