package com.xhc.sbm.ws.bean.jaxbbean.space2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: xhc
 * @Date: 2020/7/6 12:03
 * @Description:
 */
@XmlType(name = "sex")
@XmlEnum
public enum SexEnum {

    MEN("1"),
    WOMEN("2");

    private String code;

    SexEnum(String code) {
        this.code = code;
    }

    public String value() {
        return name();
    }

    public static SexEnum fromValue(String v) {
        return valueOf(v);
    }

    public String getCode(){
        return code;
    }



    public static void main(String[] args) {
        System.out.println(SexEnum.MEN.value());
        System.out.println(SexEnum.WOMEN.getCode());
    }
}
