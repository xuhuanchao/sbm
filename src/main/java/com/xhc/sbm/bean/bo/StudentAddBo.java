package com.xhc.sbm.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: xhc
 * @Date: 2020/4/1 15:12
 * @Description:
 */
@Data
@ApiModel
public class StudentAddBo {

    @ApiModelProperty(value = "姓名", required = true)
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "年龄", required = true)
    @NotNull(message = "年龄不能为空")
    private Integer age;
}
