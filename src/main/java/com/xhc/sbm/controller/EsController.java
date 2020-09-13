package com.xhc.sbm.controller;

import com.xhc.sbm.bean.es.Item;
import com.xhc.sbm.bean.ResponseResult;
import com.xhc.sbm.service.IItemservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/6/10 17:51
 * @Description:
 */
@RestController
@RequestMapping("es")
@ConditionalOnProperty(prefix = "spring.data.elasticsearch", name = "cluster-nodes", matchIfMissing = false)
public class EsController {

    @Autowired
    private IItemservice itemService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/createItemIndex")
    public ResponseResult createItemIndex(){
        elasticsearchTemplate.createIndex(Item.class);
        elasticsearchTemplate.putMapping(Item.class);
        return ResponseResult.success();
    }

    @PostMapping("/deleteItemIndex")
    public ResponseResult deleteItemIndex(){
        elasticsearchTemplate.deleteIndex(Item.class);
        return ResponseResult.success();
    }


    @PostMapping("addItem")
    public ResponseResult addItem(@RequestBody Item item){
        itemService.addItem(item);
        return ResponseResult.success();
    }


    @PostMapping("modifyItem")
    public ResponseResult modifyItem(@RequestBody Item item){
        itemService.modify(item);
        return ResponseResult.success();
    }


    @DeleteMapping("deleteItem")
    public ResponseResult deleteItem(@RequestBody Item item){
        itemService.deleteItem(item);
        return ResponseResult.success();
    }

    @PostMapping("queryAllItem")
    public ResponseResult<List<Item>> queryAllItem(){
        List<Item> list = itemService.findAll();
        ResponseResult result = ResponseResult.success(list);
        return result;
    }

    @PostMapping("queryPageItem")
    public ResponseResult<List<Item>> queryPageItem(@RequestParam(value = "页码从0开始", required = true) int page,
                                                    @RequestParam(value = "每页记录数，默认10", defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Item> list = itemService.findPage(pageRequest);
        ResponseResult result = ResponseResult.success(list);
        return result;
    }
}
