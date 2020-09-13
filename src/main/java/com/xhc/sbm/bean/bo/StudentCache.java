package com.xhc.sbm.bean.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/3/27 9:43
 * @Description:
 */
@Data
public class StudentCache {

    @NotNull(message = "id不能为空")
    private Long id;

    private String name;

    private Integer age;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

}
