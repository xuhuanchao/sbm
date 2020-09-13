package com.xhc.sbm.controller;

import com.xhc.sbm.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: xhc
 * @Date: 2020/6/15 22:28
 * @Description:
 */
@RestController
@RequestMapping("batch")
@Api(tags = "spring batch 测试")
public class SpringBatchController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JobLauncher jobLauncher;


    private final String defaultJob = "salaryJob";

    @PostMapping("/startSalaryBatch")
    @ApiOperation(value = "启动指定job", notes="启动指定job")
    @Async
    public ResponseResult startSalaryBatch(@RequestParam(value = "jobName", required = false) String jobName) throws Exception{
        String job = jobName;
        if(StringUtils.isEmpty(jobName)){
            job = defaultJob;
        }
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
        jobLauncher.run(applicationContext.getBean(job, Job.class), jobParameters);
        return ResponseResult.success();
    }
}
