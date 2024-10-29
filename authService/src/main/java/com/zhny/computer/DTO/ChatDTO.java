package com.zhny.computer.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class ChatDTO {
    private List<Map<String, Object>> messages;
}
