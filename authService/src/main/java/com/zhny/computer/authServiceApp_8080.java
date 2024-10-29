package com.zhny.computer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class authServiceApp_8080 {
    public static void main(String[] args) {
        SpringApplication.run(authServiceApp_8080.class, args);
    }
}
