package com.xhc.sbm.ws;

import com.xhc.sbm.ws.bean.BallResponse;
import com.xhc.sbm.ws.bean.QueryBallRequest;
import com.xhc.sbm.ws.constants.BallColorEnum;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/2 18:03
 * @Description: 使用jdk自带@WebService注解创建服务
 * 使用 wsimport -encoding utf-8 -s ./ws2/ http://localhost:8800/ball?wsdl -keep 命令创建客户端代码
 */
@WebService
public class BallWebService implements IBallWebService {

    @Override
    public BallResponse queryBallByName(String name) {
        BallResponse ballResponse = new BallResponse();
        ballResponse.setName("足球");
        ballResponse.setColor(BallColorEnum.blue);
        ballResponse.setPrice(14.6f);
        return ballResponse;
    }

    @Override
    public List<BallResponse> queryBall(QueryBallRequest queryBallRequest) {
        List<BallResponse> list = new ArrayList<>();
        BallResponse ballResponse = new BallResponse();
        ballResponse.setName("足球");
        ballResponse.setColor(BallColorEnum.blue);
        ballResponse.setPrice(14.6f);

        list.add(ballResponse);
        return list;
    }
}
