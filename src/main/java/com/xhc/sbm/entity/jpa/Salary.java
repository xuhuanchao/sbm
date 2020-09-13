package com.xhc.sbm.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/6/15 11:36
 * @Description:
 */
@Data
@Entity
@Table(name = "salary")
public class Salary {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "payday")
    private Date payday;

    @Column(name = "total_num")
    private Float totalNum;

    @Column(name = "base_num")
    private Float baseNum;

    @Column(name = "achieve_num")
    private Float achieveNum;

    @Column(name = "achieve_percent")
    private Integer achievePercent;

    @Column(name = "deduct_num")
    private Float deductNum;

    @Column(name = "bonus_num")
    private Float bonusNum;

    //1已删除，0未删除
    @Column(name = "deleted")
    private Integer deleted;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


}
