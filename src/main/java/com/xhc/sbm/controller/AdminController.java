package com.xhc.sbm.controller;

import com.xhc.sbm.bean.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xhc
 * @Date: 2020/4/23 10:52
 * @Description:
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @RequestMapping(value = "/test")
    public ResponseResult test(){
        return ResponseResult.success();
    }
}
