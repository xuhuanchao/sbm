package com.xhc.sbm.batch.config;

import com.xhc.sbm.batch.salary.*;
import com.xhc.sbm.entity.jpa.Salary;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.persistence.EntityManagerFactory;

/**
 * @Author: xhc
 * @Date: 2020/6/15 11:04
 * @Description: 工资处理job，两个step。step1:从inputSalary.csv文件中读取工资信息，写入到数据库。step2:从数据库读取工资信息，写入到outputSalary.csv
 */
@SpringBootConfiguration
public class SalaryJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * salaryJob：2个step。1.从文件读取工资信息存入数据库。 2.从数据库读取工资写入文件。
     * @param salaryJobListener
     * @param importSalaryStep
     * @param outputSalaryFileStep
     * @return
     */
    @Bean
    public Job salaryJob(SalaryJobListener salaryJobListener,
                               Step importSalaryStep,
                               Step outputSalaryFileStep) {
        return jobBuilderFactory.get("salaryJob")
                .incrementer(new RunIdIncrementer())
                .listener(salaryJobListener)
                .flow(importSalaryStep)
                .next(outputSalaryFileStep)
                .end()
                .build();
    }


    /**
     * importSalaryStep：从文件读取工资导入到数据库
     * @param salaryFlatFileItemReader
     * @param salaryJpaItemWriter
     * @param processor
     * @return
     */
    @Bean
    public Step importSalaryStep(FlatFileItemReader<Salary> salaryFlatFileItemReader,
                                 ItemWriter<Salary> salaryJpaItemWriter,
                                 ItemProcessor processor) {
        return stepBuilderFactory.get("importSalaryStep")
                .<Salary, Salary>chunk(50)
                .reader(salaryFlatFileItemReader)
                //.faultTolerant() 启动容错
                //.retry(DeadlockLoserDataAccessException.class) 需要重试的异常 //.retryLimit(3) 重试最大次数
                //.skip(Exception.class) 可以跳过的异常  //.skipLimit(1) 可以跳过的次数，超过step会失败
                .listener(new SalaryReadFileListener())
                .processor(processor)
                .writer(salaryJpaItemWriter)//.faultTolerant().skip(Exception.class).skipLimit(1)
                .listener(new SalaryWriteJpaListener())
                .taskExecutor(new SimpleAsyncTaskExecutor()).throttleLimit(3)  //使用多线程执行step，以及最大线程数量
                .build();
    }


    /**
     * outputSalaryFileStep：从数据库将工资信息输出到文件的
     * @param jpaPagingItemReader
     * @param flatFileItemWriter
     * @param processor
     * @return
     */
    @Bean
    public Step outputSalaryFileStep(JpaPagingItemReader<Salary> jpaPagingItemReader,
                                 FlatFileItemWriter<Salary> flatFileItemWriter,
                                 ItemProcessor processor) {
        return stepBuilderFactory.get("outputSalaryFileStep")
                .<Salary, Salary>chunk(50)
                .reader(jpaPagingItemReader)
                .listener(new SalaryReadJpaListener())
                .processor(processor)
                .writer(flatFileItemWriter)//.faultTolerant().skip(Exception.class).skipLimit(1)
                .listener(new SalaryWriteFileListener())
                .build();
    }


    /**
     * reader in importSalaryStep
     * @return
     */
    @Bean
    public FlatFileItemReader<Salary> salaryFlatFileItemReader() {
/*        return new FlatFileItemReaderBuilder<Salary>()
                .name("salaryFlatFileItemReader")
                .resource(new ClassPathResource("inputSalary.csv"))
                .delimited()
                .names(new String[]{"name", "age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Salary>() {{
                    setTargetType(Salary.class);
                }})
                .build();*/
        FlatFileItemReader<Salary> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("batch/inputSalary.csv"));
        reader.setLineMapper(new SalaryLineMapper());
        return reader;
    }

    /**
     * processor in importSalaryStep
     * @return
     */
    @Bean
    public ItemProcessor processor(){
        return new SalaryFileToDbProcessor();
    }

    /**
     * writer in importSalaryStep
     * @param entityManager
     * @return
     */
    @Bean
    public JpaItemWriter<Salary> salaryJpaItemWriter(EntityManagerFactory entityManager) {
        JpaItemWriter<Salary> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager);
        return writer;
    }


    /**
     * listener in importSalaryStep
     * @return
     */
    @Bean
    public SalaryReadFileListener salaryReadListener(){
        return new SalaryReadFileListener();
    }

    /**
     * listener in job
     * @return
     */
    @Bean
    public SalaryJobListener jobCompletionNotificationListener(){
        return new SalaryJobListener();
    }


    /**
     * outputSalaryFileStep 的reader
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public JpaPagingItemReader<Salary> jpaPagingItemReader(EntityManagerFactory entityManagerFactory ){
        JpaPagingItemReader<Salary> salaryJpaPagingItemReader = new JpaPagingItemReader<>();
        salaryJpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        JpaNativeQueryProvider<Salary> jpaQueryProvider = new JpaNativeQueryProvider<Salary>();
        jpaQueryProvider.setEntityClass(Salary.class);
        jpaQueryProvider.setSqlQuery("select * from Salary where create_time between '2020-06-17 00:00:00' and '2020-06-17 23:59:59'");
        salaryJpaPagingItemReader.setQueryProvider(jpaQueryProvider);
        salaryJpaPagingItemReader.setPageSize(20);
        return  salaryJpaPagingItemReader;
    }


    /**
     * outputSalaryFileStep 的 writer
     * @return
     */
    @Bean
    public FlatFileItemWriter<Salary> flatFileItemWriter(){
        FlatFileItemWriter<Salary> salaryFlatFileItemWriter = new FlatFileItemWriter<>();
        salaryFlatFileItemWriter.setEncoding("utf-8");
        salaryFlatFileItemWriter.setAppendAllowed(true);
        salaryFlatFileItemWriter.setResource(new ClassPathResource("batch/outputSalary.csv"));

        SalaryLineAggregator salaryLineAggregator = new SalaryLineAggregator();
        salaryFlatFileItemWriter.setLineAggregator(salaryLineAggregator);

        return salaryFlatFileItemWriter;
    }




}
