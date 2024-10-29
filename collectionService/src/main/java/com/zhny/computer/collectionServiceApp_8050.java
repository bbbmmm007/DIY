package com.zhny.computer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhny.computer.mapper")
public class collectionServiceApp_8050 {
    public static void main(String[] args) {
        SpringApplication.run(collectionServiceApp_8050.class, args);
    }
}
