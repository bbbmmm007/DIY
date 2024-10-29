package com.zhny.computer;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhny.computer.mapper")
public class knowledgeServiceApp_8060 {
    public static void main(String[] args) {
        SpringApplication.run(knowledgeServiceApp_8060.class, args);
    }
}
