package com.xhc.sbm.dao.es;

import com.xhc.sbm.bean.es.Item;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: xhc
 * @Date: 2020/6/10 18:01
 * @Description:
 */
@ConditionalOnProperty(prefix = "spring.data.elasticsearch", name = "cluster-nodes", matchIfMissing = false)
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

}
