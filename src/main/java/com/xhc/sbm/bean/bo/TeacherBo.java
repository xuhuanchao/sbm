package com.xhc.sbm.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;


/**
 * @Author: xhc
 * @Date: 2020/6/11 18:17
 * @Description:
 */
@Data
public class TeacherBo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    @Range(min = 1, max = 2, message = "性别取值：1男，2女")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    @Range(min = 0, max = 999, message = "年龄取值范围：0~999")
    private Integer age;


}
