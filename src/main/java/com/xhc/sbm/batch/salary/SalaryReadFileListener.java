package com.xhc.sbm.batch.salary;

import lombok.extern.log4j.Log4j2;

import javax.batch.api.chunk.listener.ItemReadListener;

/**
 * @Author: xhc
 * @Date: 2020/6/15 22:02
 * @Description: 从文件读工资信息监听
 */
@Log4j2
public class SalaryReadFileListener implements ItemReadListener {

    @Override
    public void beforeRead() throws Exception {
        log.debug("开始read");
    }

    @Override
    public void afterRead(Object o) throws Exception {
        log.debug("结束read,{}", o.toString());
    }

    @Override
    public void onReadError(Exception e) throws Exception {
        log.error("读取工资信息异常", e);
    }
}
