package com.zhny.computer;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhny.computer.mapper")
public class profileCartServiceApp_8040 {
    public static void main(String[] args) {
        SpringApplication.run(profileCartServiceApp_8040.class, args);
    }
}
