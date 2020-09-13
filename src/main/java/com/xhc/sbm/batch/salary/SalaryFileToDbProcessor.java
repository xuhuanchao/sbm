package com.xhc.sbm.batch.salary;

import com.xhc.sbm.entity.jpa.Salary;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

/**
 * @Author: xhc
 * @Date: 2020/6/15 15:44
 *
 * @Description: 工资读取到数据库step中，批处理过程
 */
@Log4j2
public class SalaryFileToDbProcessor implements ItemProcessor<Salary, Salary> {


    @Override
    public Salary process(Salary salary) throws Exception {
        log.info(salary.toString());
        return salary;
    }
}
