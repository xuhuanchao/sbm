package com.xhc.sbm.service;

import com.xhc.sbm.bean.es.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/6/10 18:22
 * @Description:
 */
public interface IItemservice {


    void addItem(Item item);

    void addItemList(List<Item> list);

    void modify(Item item);

    void deleteItem(Item item);

    List<Item> findAll();

    List<Item> findPage(Pageable pageable);
}
