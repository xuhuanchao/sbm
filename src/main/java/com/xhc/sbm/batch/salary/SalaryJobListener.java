package com.xhc.sbm.batch.salary;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 *  工资job监听
 */
@Log4j2
public class SalaryJobListener extends JobExecutionListenerSupport {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.debug("==>job开始！job Id:{}，job名称:{}", jobExecution.getJobId(), jobExecution.getJobConfigurationName());
    	super.beforeJob(jobExecution);
    }

    @Override
	public void afterJob(JobExecution jobExecution) {
		log.info(" ==>job结束！job Id:{}，job名称:{}，结果状态{}", jobExecution.getJobId(), jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
	}
}
