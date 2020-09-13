package com.xhc.sbm.ws.bean.jaxbbean;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @Author: xhc
 * @Date: 2020/7/6 12:15
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id","name","sex"
})
@XmlRootElement(name = "personRequest")
@Data
public class PersonRequest {

    @XmlElement(required = true)
    private Integer id;

    @XmlElement(required = false)
    private String name;

    @XmlElement(required = false)
    private SexEnum sex;
}
