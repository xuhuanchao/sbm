package com.xhc.sbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableJms

//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.xhc.sbm.service.authenticity.*")}) 禁止扫描包
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.xhc.sbm.batch.config.*")})
public class SbmApplication {

	public static void main(String[] args) {
//		System.setProperty("es.set.netty.runtime.available.processors","false");   //处理elasticSearch和redis使用netty时的冲突
		SpringApplication.run(SbmApplication.class, args);
	}

}
