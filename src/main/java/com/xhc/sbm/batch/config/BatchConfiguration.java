package com.xhc.sbm.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @Author: xhc
 * @Date: 2020/6/17 22:35
 * @Description:  配置job
 * spring batch默认使用 {@link org.springframework.batch.core.configuration.annotation.ModularBatchConfiguration}，
 * 其中configurers默认使用{@link JpaBatchConfigurer}，
 * 在创建JobLauncher{@link JpaBatchConfigurer#createJobLauncher()}时创建的同步执行器SyncTaskExecutor，可以使用SimpleAsyncTaskExecutor异步任务执行器来执行Job
 *
 */
@SpringBootConfiguration
@EnableBatchProcessing(modular = true)
public class BatchConfiguration {

    @Bean
    public ApplicationContextFactory salaryJobContext(JobLauncher jobLauncher){
        //设置jobLauncher中的taskExecutor为异步执行线程
        if(jobLauncher instanceof SimpleJobLauncher){
            ((SimpleJobLauncher)jobLauncher).setTaskExecutor(new SimpleAsyncTaskExecutor());
        }
        return new GenericApplicationContextFactory(SalaryJobConfig.class);
    }


    @Bean
    public ApplicationContextFactory helloJobContext(){
        return new GenericApplicationContextFactory(HelloJobConfig.class);
    }
}
