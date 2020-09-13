package com.xhc.sbm.dao;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/3/27 11:44
 * @Description:
 */
public interface BaseDao<T, R> {

    T queryOne(R t);

    List<T> queryAll(T t);

    Integer addOne(T t);

    Integer updateOne(T t);

    Integer delOne(R r);
}
