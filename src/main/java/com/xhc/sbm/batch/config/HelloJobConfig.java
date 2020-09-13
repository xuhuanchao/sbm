package com.xhc.sbm.batch.config;

import com.xhc.sbm.entity.jpa.Teacher;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.batch.api.chunk.listener.ItemReadListener;
import javax.batch.api.chunk.listener.ItemWriteListener;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/6/18 11:12
 * @Description:
 */
@Log4j2
@SpringBootConfiguration
public class HelloJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory2;


    @Bean
    public Job helloJob(JobExecutionListener jobExecutionListener,
                         Step helloStep) {
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .flow(helloStep)
                .end()
                .build();
    }

    @Bean
    public Step helloStep(@Qualifier("helloFlatFileItemReader") FlatFileItemReader<Teacher> helloFlatFileItemReader,
                          FlatFileItemWriter<String> helloFlatFileItemWriter,
                          ItemProcessor helloProcessor,
                          ItemReadListener itemReadListener,
                          ItemWriteListener itemWriteListener) {
        return stepBuilderFactory2.get("helloStep")
                .<Teacher, String>chunk(5)
                .reader(helloFlatFileItemReader)
                .faultTolerant() //启动容错
                //.retry(DeadlockLoserDataAccessException.class) //需要重试的异常 //.retryLimit(3) 重试最大次数
                //.skip(Exception.class) //可以跳过的异常  //.skipLimit(1) 可以跳过的次数，超过step会失败
                .listener(itemReadListener)
                .processor(helloProcessor)
                .writer(helloFlatFileItemWriter)//.faultTolerant().skip(Exception.class).skipLimit(1)
                .listener(itemWriteListener)
                .taskExecutor(new SimpleAsyncTaskExecutor()).throttleLimit(1)  //使用多线程执行step，以及最大线程数量
                .build();
    }


    @Bean
    public FlatFileItemReader<Teacher> helloFlatFileItemReader() {
        FlatFileItemReader<Teacher> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("batch/inputHello.txt"));
        LineMapper<Teacher> lineMapper = new LineMapper() {
            @Override
            public Teacher mapLine(String line, int lineNumber) throws Exception {
                String[] args = line.split(",");

                Teacher teacher = new Teacher();
                teacher.setName(args[0]);
                teacher.setSex(Integer.valueOf(args[1]));
                teacher.setAge(Integer.valueOf(args[2]));
                return teacher;
            }
        };
        reader.setLineMapper(lineMapper);
        return reader;
    }


    @Bean
    public ItemProcessor<Teacher, String> helloProcessor(){
        ItemProcessor<Teacher, String> itemProcessor = new ItemProcessor<Teacher, String>() {
            @Override
            public String process(Teacher teacher) throws Exception {
                return String.format("%s 老师，性别%s，年龄%d ", teacher.getName(), teacher.getSex().equals(1)? "男":"女", teacher.getAge());
            }
        };
        return itemProcessor;
    }


    @Bean("helloFlatFileItemWriter")
    public FlatFileItemWriter<String> helloFlatFileItemWriter() {
        FlatFileItemWriter<String> salaryFlatFileItemWriter = new FlatFileItemWriter<>();
        salaryFlatFileItemWriter.setEncoding("utf-8");
        salaryFlatFileItemWriter.setAppendAllowed(true);
        salaryFlatFileItemWriter.setResource(new ClassPathResource("batch/outputHello.txt"));

        PassThroughLineAggregator<String> lineAggregator = new PassThroughLineAggregator<>();
        salaryFlatFileItemWriter.setLineAggregator(lineAggregator);

        return salaryFlatFileItemWriter;
    }

    @Bean
    public ItemReadListener itemReadListener(){
        ItemReadListener itemReadListener = new ItemReadListener() {
            @Override
            public void beforeRead() throws Exception {

            }

            @Override
            public void afterRead(Object o) throws Exception {

            }

            @Override
            public void onReadError(Exception e) throws Exception {
                log.error("读取文件时异常", e);
            }
        };
        return itemReadListener;
    }

    @Bean
    public ItemWriteListener itemWriteListener(){
        return new ItemWriteListener() {
            @Override
            public void beforeWrite(List<Object> list) throws Exception {
                log.info("准备写入的内容：{}", list.toString());
            }

            @Override
            public void afterWrite(List<Object> list) throws Exception {

            }

            @Override
            public void onWriteError(List<Object> list, Exception e) throws Exception {
                log.error("写入信息时异常", e);
            }
        };
    }

    @Bean
    public JobExecutionListener jobExecutionListener(){
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("准备开始执行[{}], job id:{}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobId());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("job[{}]执行结束。job id:{}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobId());
            }
        };
    }
}
