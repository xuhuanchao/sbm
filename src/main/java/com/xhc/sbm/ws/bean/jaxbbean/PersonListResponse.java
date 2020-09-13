package com.xhc.sbm.ws.bean.jaxbbean;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/7/6 15:45
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "persons"
})
@XmlRootElement(name = "personListResponse")
@Data
public class PersonListResponse {

    @XmlElementWrapper(name="persons")
    @XmlElement(name="personResponse")
    private List<PersonResponse> persons;
}
