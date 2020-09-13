package com.xhc.sbm.ws.constants;


/**
 * @Author: xhc
 * @Date: 2020/7/2 17:58
 * @Description:
 */
public enum BallColorEnum {

    red("red", 1),
    blue("blue", 2),
    black("black", 3);


    private String name;
    private Integer value;

    BallColorEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }


    public static Integer getValueByName(String name) {
        for(BallColorEnum item : values() ){
            if(name.equals(item.name)){
                return item.value;
            }
        }
        return null;
    }
}
