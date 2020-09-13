package com.xhc.sbm.batch.salary;

import lombok.extern.log4j.Log4j2;

import javax.batch.api.chunk.listener.ItemWriteListener;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/6/15 22:04
 * @Description: 向数据库写入工资监听
 */
@Log4j2
public class SalaryWriteJpaListener implements ItemWriteListener {
    @Override
    public void beforeWrite(List<Object> list) throws Exception {

    }

    @Override
    public void afterWrite(List<Object> list) throws Exception {

    }

    @Override
    public void onWriteError(List<Object> list, Exception e) throws Exception {
        log.error("写入工资信息时异常", e);
    }
}
