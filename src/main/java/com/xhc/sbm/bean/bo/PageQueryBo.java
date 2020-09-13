package com.xhc.sbm.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: xhc
 * @Date: 2020/4/1 23:45
 * @Description:
 */
@Data
@ApiModel
public class PageQueryBo {

    @ApiModelProperty(value = "页码", required = true)
    @NotNull(message = "页码不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页记录数", required = true)
    @NotNull(message = "每页记录数不能为空")
    private Integer pageSize;
}
