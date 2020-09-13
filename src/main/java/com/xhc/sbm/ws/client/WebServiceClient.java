package com.xhc.sbm.ws.client;

import com.xhc.sbm.ws.bean.jaxbbean.PersonListRequest;
import com.xhc.sbm.ws.bean.jaxbbean.PersonListResponse;
import com.xhc.sbm.ws.bean.jaxbbean.PersonRequest;
import com.xhc.sbm.ws.bean.jaxbbean.PersonResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/3 11:26
 * @Description: 负责调用web service服务的网关，继承{@link WebServiceGatewaySupport}，
 * 内置WebServiceTemplate，可以设置Marshaller 和 Unmarshaller，可设置WebServiceTemplate的DefaultUri
 */
public class WebServiceClient extends WebServiceGatewaySupport {

    public PersonResponse testPerson(){
        String uri = "http://localhost:8081/ws";
        PersonRequest personRequest = new PersonRequest();
        personRequest.setId(1);
        personRequest.setName("mike");
        PersonResponse result = (PersonResponse)getWebServiceTemplate().marshalSendAndReceive(uri, personRequest);
        return result;
    }



    public String simpleSendAndReceive() {
        String uri = "http://localhost:8081/ws";
        String message = "<ns2:personRequest xmlns:ns2=\"http://xhc.com.cn/sbmService\"><ns2:id>1</ns2:id><ns2:name>mike</ns2:name></ns2:personRequest>";
        StreamSource source = new StreamSource(new StringReader(message));
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        getWebServiceTemplate().sendSourceAndReceiveToResult(uri, source, result);
        return sw.toString();
    }


    public String simpleSendAndReceive2(){
        String uri = "http://localhost:8081/ws";
        String message = "<ns2:personRequest xmlns:ns2=\"http://xhc.com.cn/sbmService2\"><ns2:id>2</ns2:id><ns2:name>mike</ns2:name></ns2:personRequest>";
        StreamSource source = new StreamSource(new StringReader(message));
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        getWebServiceTemplate().sendSourceAndReceiveToResult(uri, source, result);
        return sw.toString();
    }

    public PersonResponse wrapObjSendAndReceive() {
        String uri = "http://localhost:8081/ws";
        PersonRequest request = new PersonRequest();
        request.setId(3);
        PersonResponse response = (PersonResponse)getWebServiceTemplate().marshalSendAndReceive(uri, request);
        return response;
    }


    public PersonListResponse wrapObjSendAndReceiveList() {
        String uri = "http://localhost:8081/ws";
        PersonListRequest request = new PersonListRequest();
        List<PersonRequest> persons = new ArrayList<>();
        PersonRequest p1 = new PersonRequest();
        p1.setId(1);
        p1.setName("张");
        PersonRequest p2 = new PersonRequest();
        p2.setId(2);
        p2.setName("李");
        persons.add(p1);
        persons.add(p2);
        request.setPersons(persons);

        PersonListResponse response = (PersonListResponse)getWebServiceTemplate().marshalSendAndReceive(uri, request);
        return response;
    }


}
