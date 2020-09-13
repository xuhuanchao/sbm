package com.xhc.sbm.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xhc
 * @Date: 2020/4/1 23:45
 * @Description:
 */
@Data
@ApiModel
public class StudentPageQueryBo extends PageQueryBo {

    @ApiModelProperty(value = "姓名", required = false)
    private String name;

    @ApiModelProperty(value = "年龄", required = false)
    private Integer age;
}
