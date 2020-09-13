package com.xhc.sbm.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/6/11 18:17
 * @Description:
 */
@Data
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    //1男，2女
    @Column(name = "sex")
    private Integer sex;

    @Column(name = "age")
    private Integer age;

    //1已删除，0未删除
    @Column(name = "deleted")
    private Integer deleted;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
