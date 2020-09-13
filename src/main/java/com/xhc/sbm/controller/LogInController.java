package com.xhc.sbm.controller;

import com.xhc.sbm.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: xhc
 * @Date: 2020/4/13 12:15
 * @Description:
 */
@Controller
@Api(tags = "登陆登出controller")
public class LogInController {

    @RequestMapping(value = "/goLogin")
    public String goLogin(ModelMap model){
        model.addAttribute("today", LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        return "login";
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(ModelMap model){
        model.addAttribute("today", LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        return "index";
    }

}
