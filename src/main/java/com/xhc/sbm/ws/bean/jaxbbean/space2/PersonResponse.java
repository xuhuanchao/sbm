package com.xhc.sbm.ws.bean.jaxbbean.space2;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @Author: xhc
 * @Date: 2020/7/6 12:00
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id","name","sex","address","salary"
})
@XmlRootElement(name = "person")
@Data
public class PersonResponse {

    @XmlElement(required = true)
    private Integer id;

    @XmlElement(required = false)
    private String name;

    @XmlElement(required = false)
    private SexEnum sex;

    @XmlElement(required = false)
    private String address;

    @XmlElement(required = false)
    private Float salary;
}
