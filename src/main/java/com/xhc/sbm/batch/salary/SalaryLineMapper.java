package com.xhc.sbm.batch.salary;

import com.xhc.sbm.entity.jpa.Salary;
import org.springframework.batch.item.file.LineMapper;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/6/15 16:19
 * @Description: 读取工资文件转换为bean的处理器
 */
public class SalaryLineMapper implements LineMapper<Salary> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Salary mapLine(String line, int lineNumber) throws Exception {
        String[] args = line.split(",");

        Salary salary = new Salary();
        salary.setTeacherId(Long.valueOf(args[0]));
        salary.setPayday(simpleDateFormat.parse(args[1]));
        salary.setTotalNum(Float.valueOf(args[2]));
        salary.setBaseNum(Float.valueOf(args[3]));
        salary.setCreateTime(new Date());

        return salary;
    }
}
