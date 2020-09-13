package com.xhc.sbm.controller;

import com.xhc.sbm.ws.bean.jaxbbean.PersonListResponse;
import com.xhc.sbm.ws.bean.jaxbbean.PersonResponse;
import com.xhc.sbm.ws.client.WebServiceClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xhc
 * @Date: 2020/7/3 11:29
 * @Description:
 * 方法 {@link WsController#simple()}、{@link WsController#simpleXpath()}、{@link WsController#wrapObj()}、{@link WsController#wrapObjList()}
 * 使用spring-ws的WebServiceTemplate发送soap
 */
@RestController
@Api(tags = "测试spring web service")
@RequestMapping("testWs")
public class WsController {


    @PostMapping("/simple")
    @ApiOperation(value = "测试简单Source、Result传输soap", notes="测试简单Source、Result传输soap")
    public String simple(){
        WebServiceClient webServiceClient = new WebServiceClient();
        webServiceClient.setDefaultUri("http://localhost:8081/ws");
        return webServiceClient.simpleSendAndReceive();
    }

    @PostMapping("/simpleXpath")
    @ApiOperation(value = "测试简单Source、Result传输soap，服务方使用xpath解析方式，并且使用另一个域名",
            notes="测试简单Source、Result传输soap，服务方使用xpath解析方式，并且使用另一个域名")
    public String simpleXpath(){
        WebServiceClient webServiceClient = new WebServiceClient();
        webServiceClient.setDefaultUri("http://localhost:8081/ws");
        return webServiceClient.simpleSendAndReceive2();
    }


    @PostMapping("/wrapObj")
    @ApiOperation(value = "测试Marshaller和Unmarshaller转换Object进行传输soap", notes="测试Marshaller和Unmarshaller转换Object进行传输soap")
    public String wrapObj(){
        WebServiceClient webServiceClient = new WebServiceClient();
        webServiceClient.setDefaultUri("http://localhost:8081/ws");

        //设置object封装和解封装
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		marshaller.setContextPath("com.xhc.sbm.ws.bean.jaxbbean.PersonRequest");
        marshaller.setPackagesToScan("com.xhc.sbm.ws.bean.jaxbbean");
        webServiceClient.setMarshaller(marshaller);
        webServiceClient.setUnmarshaller(marshaller);

        PersonResponse personResponse = webServiceClient.wrapObjSendAndReceive();
        return personResponse.toString();
    }

    /**
     * 使用spring-ws的WebServiceTemplate发送soap
     * @return
     */
    @PostMapping("/wrapObjList")
    @ApiOperation(value = "测试Marshaller和Unmarshaller转换Object List进行传输soap", notes="测试Marshaller和Unmarshaller转换Object List进行传输soap")
    public String wrapObjList(){
        WebServiceClient webServiceClient = new WebServiceClient();
        webServiceClient.setDefaultUri("http://localhost:8081/ws");
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		marshaller.setContextPath("com.xhc.sbm.ws.bean.jaxbbean.PersonRequest");
        marshaller.setPackagesToScan("com.xhc.sbm.ws.bean.jaxbbean");
        webServiceClient.setMarshaller(marshaller);
        webServiceClient.setUnmarshaller(marshaller);

        PersonListResponse personListResponse = webServiceClient.wrapObjSendAndReceiveList();
        return personListResponse.toString();
    }
}
