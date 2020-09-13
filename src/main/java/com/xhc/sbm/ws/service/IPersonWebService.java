package com.xhc.sbm.ws.service;

import com.xhc.sbm.ws.bean.jaxbbean.PersonListRequest;
import com.xhc.sbm.ws.bean.jaxbbean.PersonResponse;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/6 12:19
 * @Description:
 */
public interface IPersonWebService {

    PersonResponse queryPersonById(Integer id);

    List<PersonResponse> queryListPerson(PersonListRequest personListRequest);

}
