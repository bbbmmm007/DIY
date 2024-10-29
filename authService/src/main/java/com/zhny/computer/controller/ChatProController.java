package com.zhny.computer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhny.computer.DTO.ChatProDTO;
import com.zhny.computer.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/chatpro")
public class ChatProController {

    @Autowired
    private ApiConfig apiConfig;

    private final String apiUrl = "https://api.moonshot.cn/v1/chat/completions";

    @PostMapping("/message")
    public String sendMessage(@RequestBody ChatProDTO request) {
        // 处理请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", request.getModel());
        requestBody.put("messages", request.getMessages());
        requestBody.put("tools", request.getTools());
        requestBody.put("name", request.getName());
        requestBody.put("description", request.getDescription());
        requestBody.put("metadata", request.getMetadata());
        requestBody.put("expired_at", request.getExpiredAt());

        // 使用 RestTemplate 发送请求
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(apiUrl, createRequestEntity(requestBody), String.class);

        // 解析返回的 JSON，提取 content
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            String assistantMessage = jsonNode.get("choices").get(0).get("message").get("content").asText();
            return assistantMessage != null ? assistantMessage : "发生错误，请稍后重试。";
        } catch (Exception e) {
            e.printStackTrace();
            return "发生错误，请稍后重试。";
        }
    }

    private HttpEntity<Map<String, Object>> createRequestEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiConfig.getApiKey());
        return new HttpEntity<>(body, headers);
    }
}
