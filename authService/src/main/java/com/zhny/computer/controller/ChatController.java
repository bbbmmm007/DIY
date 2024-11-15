package com.zhny.computer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhny.computer.DTO.ChatDTO;
import com.zhny.computer.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ApiConfig apiConfig;

    private final String apiUrl = "https://api.moonshot.cn/v1/chat/completions";



    @PostMapping("message")
    public String sendMessage(@RequestBody ChatDTO request) {
        List<Map<String, Object>> messages = request.getMessages();

        String userMessage = (String) messages.stream()
                .filter(msg -> "user".equals(msg.get("role")))
                .findFirst()
                .map(msg -> msg.get("content"))
                .orElse("");


        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "moonshot-v1-8k");
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.3);

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


    private List<Map<String, Object>> createMessages(String userMessage) {
        Map<String, Object> user = new HashMap<>();
        user.put("role", "user");
        user.put("content", userMessage);

        Map<String, Object> system = new HashMap<>();
        system.put("role", "system");
        system.put("content", "你是 Kimi，如果用户提问代码的问题,在代码前一定要加上 代码: 的字样");

        // 创建一个 List 来返回消息数组
        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(system);
        messages.add(user);

        return messages; // 返回消息列表
    }


    private HttpEntity<Map<String, Object>> createRequestEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiConfig.getApiKey());
        return new HttpEntity<>(body, headers);
    }
}
