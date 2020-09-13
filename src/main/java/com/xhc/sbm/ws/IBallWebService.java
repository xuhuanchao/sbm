package com.xhc.sbm.ws;

import com.xhc.sbm.ws.bean.BallResponse;
import com.xhc.sbm.ws.bean.QueryBallRequest;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/2 18:02
 * @Description:
 */
@WebService(targetNamespace = "http://xhc.com.cn/ws")
public interface IBallWebService {

    @WebMethod
    BallResponse queryBallByName(String name);

    @WebMethod
    List<BallResponse> queryBall(QueryBallRequest queryBallRequest);
}
