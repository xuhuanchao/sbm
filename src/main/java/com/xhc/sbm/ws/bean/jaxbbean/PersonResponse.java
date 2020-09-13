package com.xhc.sbm.ws.bean.jaxbbean;

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
@XmlRootElement(name = "personResponse")
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

    @Override
    public String toString() {
        return "PersonResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }
}
