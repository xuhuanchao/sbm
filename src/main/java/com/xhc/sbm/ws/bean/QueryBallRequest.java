package com.xhc.sbm.ws.bean;

import com.xhc.sbm.ws.constants.BallColorEnum;
import lombok.Data;

/**
 * @Author: xhc
 * @Date: 2020/7/2 17:57
 * @Description:
 */
@Data
public class QueryBallRequest {

    private String name;

    private BallColorEnum color;
}
