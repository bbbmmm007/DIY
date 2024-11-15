package com.zhny.computer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenConfig {
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

}
