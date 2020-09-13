package com.xhc.sbm.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/3/27 9:43
 * @Description:
 */
@Data
public class Student {

    private Long id;

    private String name;

    private Integer age;

    private Date createTime;

    private String nameAndAge;
}
