package com.xhc.sbm.batch.salary;

import com.xhc.sbm.entity.jpa.Salary;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.text.SimpleDateFormat;

/**
 * @Author: xhc
 * @Date: 2020/6/17 20:31
 * @Description: 工资输出step中文件bean转字符处理器
 */
public class SalaryLineAggregator implements LineAggregator<Salary> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

    @Override
    public String aggregate(Salary salary) {
        return String.format("教师id=%s，发放月份=%s，实发总数=%.2f。其中工资基数=%.2f，绩效工资=%.2f，绩效发放百分比=%s，扣款=%.2f，奖金=%.2f",
                salary.getTeacherId(),
                sdf.format(salary.getPayday()),
                salary.getTotalNum(),
                salary.getBaseNum(),
                salary.getAchieveNum(),
                salary.getAchievePercent() == null ? "" : salary.getAchievePercent().toString(),
                salary.getDeductNum(),
                salary.getBonusNum());
    }
}
