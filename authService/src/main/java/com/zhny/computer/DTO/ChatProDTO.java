package com.zhny.computer.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChatProDTO {
    private String model;
    private List<Map<String, Object>> messages;
    private List<Map<String, Object>> tools;
    private String name;
    private String description;
    private Map<String, Object> metadata;
    private Long expiredAt;
}
