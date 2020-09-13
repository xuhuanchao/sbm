package com.xhc.sbm.ws.bean.jaxbbean;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @Author: xhc
 * @Date: 2020/7/6 15:28
 * @Description:
 */

@Data
public class Person {

    private Integer id;

    private String name;

    private SexEnum sex;

    private String address;

    private Float salary;
}
