package com.xhc.sbm;

import com.xhc.sbm.entity.Student;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes={SbmApplication.class}) // 指定启动类
@Log4j2
class SbmApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

//	@Test
	void contextLoads() {
		ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:8080/student/queryOne/1", Student.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		Assertions.assertEquals(HttpStatus.OK, statusCode);


		Student student = responseEntity.getBody();
		log.info("查询到的结果：" + student);

	}

}
