package com.xhc.sbm.config.ws;

import com.xhc.sbm.ws.bean.jaxbbean.PersonListRequest;
import com.xhc.sbm.ws.bean.jaxbbean.PersonListResponse;
import com.xhc.sbm.ws.bean.jaxbbean.PersonRequest;
import com.xhc.sbm.ws.bean.jaxbbean.PersonResponse;
import com.xhc.sbm.ws.service.IPersonWebService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.ws.soap.SoapHeader;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/6 11:58
 * @Description:  使用spring-boot-starter-web-services功能创建 endpoint
 */
@Endpoint
public class PersonEndpoint {

    private static final String NAMESPACE_URI = "http://xhc.com.cn/sbmService";
    private static final String NAMESPACE_URI2 = "http://xhc.com.cn/sbmService2";

    @Autowired
    private IPersonWebService personWebService;


    /**
     * [@RequestPayload] 使用payload作为方法入参签名
     * [@ResponsePayload] 使用payload作为返回值内容
     * localPart对应payload中body的第一个标签名
     * PersonRequest 中@XmlRootElement(name = "personRequest") name 要和localPart一致
     *
     *  测试时可以接收的请求：
     *  <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
     *      <SOAP-ENV:Header/>
     *      <SOAP-ENV:Body>
     *          <ns2:personRequest xmlns:ns2="http://xhc.com.cn/sbmService">
     *              <ns2:id>1</ns2:id>
     *              <ns2:name>mike</ns2:name>
     *          </ns2:personRequest>
     *      </SOAP-ENV:Body>
     *  </SOAP-ENV:Envelope>
     *  使用Content-Type=text/xml发送
     *
     *
     * @param personRequest
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "personRequest")
    @ResponsePayload
    public PersonResponse queryPersonById(@RequestPayload PersonRequest personRequest, SoapHeader header){
        PersonResponse personResponse = personWebService.queryPersonById(personRequest.getId());
        return personResponse;
    }

    /**
     * 使用xpath确定方法入参签名使用。同时使用另一个域名
     * @param id
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI2, localPart = "personRequest")
    @Namespace(prefix = "ns2", uri=NAMESPACE_URI2)
    @ResponsePayload
    public com.xhc.sbm.ws.bean.jaxbbean.space2.PersonResponse getOrder(@XPathParam("/ns2:personRequest/ns2:id") Integer id) {
        PersonResponse personResponse = personWebService.queryPersonById(id);
        com.xhc.sbm.ws.bean.jaxbbean.space2.PersonResponse result = new com.xhc.sbm.ws.bean.jaxbbean.space2.PersonResponse();
        BeanUtils.copyProperties(personResponse, result);
        return result;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "personListRequest")
    @ResponsePayload
    public PersonListResponse queryListPerson(@RequestPayload PersonListRequest personListRequest){
        PersonListResponse result = new PersonListResponse();
        List<PersonResponse> personResponses = personWebService.queryListPerson(personListRequest);
        result.setPersons(personResponses);
        return result;
    }
}
