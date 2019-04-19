package com.sg.cristina;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("com.sg.cristina.dao")
public class CristinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CristinaApplication.class, args);
	}

}
