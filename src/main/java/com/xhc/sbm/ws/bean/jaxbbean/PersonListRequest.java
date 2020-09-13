package com.xhc.sbm.ws.bean.jaxbbean;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/6 15:55
 * @Description:  @XmlType propOrder 要包含所有字段
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "persons"
})
@XmlRootElement(name = "personListRequest")
@Data
public class PersonListRequest {

    @XmlElementWrapper(name="persons")
    @XmlElement(name="personRequest")
    private List<PersonRequest> persons;
}
