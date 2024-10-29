package com.zhny.computer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@MapperScan("com.zhny.computer.mapper")
public class productServiceApp_8020 {

    public static void main(String[] args) {
        SpringApplication.run(productServiceApp_8020.class, args);
    }

}