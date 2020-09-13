package com.xhc.sbm.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/4/14 16:56
 * @Description:
 */
@Data
public class User {

    private Long id;
    private String accountName;
    private String password;
    private String userName;
    private Date createTime;
    private Integer deleted;  //1已删除、0未删除

}
