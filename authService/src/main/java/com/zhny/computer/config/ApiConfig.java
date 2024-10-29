package com.zhny.computer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Value("${moonshot.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
