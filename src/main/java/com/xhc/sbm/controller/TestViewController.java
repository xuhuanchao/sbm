package com.xhc.sbm.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/4/8 16:03
 * @Description:
 */
@Controller
@Api(tags = "测试spring mvc 跳转页面 controller")
public class TestViewController {

//
//    @RequestMapping(value = "")
//    public String index(){
//        return "hello";
//    }

    @GetMapping(value = "/view/test")
    public String test(ModelMap model){
//        ModelAndView mv = new ModelAndView("mv");
//        mv.addObject("today", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
//        mv.setViewName("hello");
        model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return "test";
    }

    @PostMapping(value = "/view/hello")
    public String hello(){
        return "hello";
    }
}
