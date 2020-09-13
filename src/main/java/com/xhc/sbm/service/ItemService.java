package com.xhc.sbm.service;

import com.xhc.sbm.bean.es.Item;
import com.xhc.sbm.dao.es.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/6/10 18:21
 * @Description:
 */
@Service
@ConditionalOnProperty(prefix = "spring.data.elasticsearch", name = "cluster-nodes", matchIfMissing = false)
public class ItemService implements IItemservice {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void addItemList(List<Item> list) {
        itemRepository.saveAll(list);
    }

    @Override
    public void modify(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public List<Item> findAll() {
        Iterable<Item> iterable = itemRepository.findAll();
        List<Item> list = new ArrayList<>();
        Iterator<Item> iterator = iterable.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    @Override
    public List<Item> findPage(Pageable pageable) {
        Page<Item> iterable = itemRepository.findAll(pageable);
        List<Item> list = new ArrayList<>();
        Iterator<Item> iterator = iterable.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }
}
