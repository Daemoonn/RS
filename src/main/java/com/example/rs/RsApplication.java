package com.example.rs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.rs.dao")
public class RsApplication {
	public static void main(String[] args) {
		SpringApplication.run(RsApplication.class, args);
	}
}
