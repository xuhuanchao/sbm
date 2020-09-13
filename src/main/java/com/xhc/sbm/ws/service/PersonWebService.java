package com.xhc.sbm.ws.service;

import com.xhc.sbm.ws.bean.jaxbbean.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: xhc
 * @Date: 2020/7/6 12:20
 * @Description:
 */
@Service
public class PersonWebService implements IPersonWebService {


    private List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void init() {
        Person p = new Person();
        p.setId(1);
        p.setName("张三");
        p.setSex(SexEnum.MEN);
        p.setSalary(2000.30f);
        p.setAddress("北京");
        persons.add(p);

        p = new Person();
        p.setId(2);
        p.setName("李四");
        p.setSex(SexEnum.MEN);
        p.setSalary(2500.30f);
        p.setAddress("天津");
        persons.add(p);

        p = new Person();
        p.setId(3);
        p.setName("王五");
        p.setSex(SexEnum.WOMEN);
        p.setSalary(3000.5f);
        p.setAddress("上海");
        persons.add(p);
    }

    @Override
    public PersonResponse queryPersonById(Integer id) {
        Person p = persons.stream().filter(person -> {
            return person.getId().equals(id);
        }).findFirst().orElse(null);
        if(p != null){
            PersonResponse personResponse = new PersonResponse();
            BeanUtils.copyProperties(p, personResponse);
            return personResponse;
        }else{
            return null;
        }
    }

    @Override
    public List<PersonResponse> queryListPerson(PersonListRequest personListRequest) {
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(1);
        personResponse.setName("张三");
        personResponse.setSex(SexEnum.MEN);
        personResponse.setSalary(2000.30f);
        personResponse.setAddress("北京");

        PersonResponse personResponse2 = new PersonResponse();
        personResponse2.setId(2);
        personResponse2.setName("李四");
        personResponse2.setSex(SexEnum.WOMEN);
        personResponse2.setSalary(3000f);
        personResponse2.setAddress("上海");

        List<PersonResponse> list = new ArrayList<>();
        list.add(personResponse);
        list.add(personResponse2);
        return list;
    }
}
