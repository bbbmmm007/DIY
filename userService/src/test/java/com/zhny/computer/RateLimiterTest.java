package com.zhny.computer;

import com.zhny.computer.DTO.UserLoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RateLimiterTest {

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOGIN_URL = "http://localhost:8080/users/login";

    @Test
    public void testLoginRateLimit() throws InterruptedException {
        // 创建 UserLoginDTO 对象
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("bm");
        loginDTO.setPassword("123");

        // 设置请求头，指定请求体为 JSON 格式
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 使用 HttpEntity 包装请求数据
        HttpEntity<UserLoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);

        // 发送 150 次登录请求
        int successfulRequests = 0;
        int failedRequests = 0;

        for (int i = 0; i < 150; i++) {
            // 使用 RestTemplate 发送 POST 请求
            ResponseEntity<String> response = restTemplate.postForEntity(
                    LOGIN_URL, requestEntity, String.class);

            if (response.getStatusCodeValue() == 500) {
                // 如果返回的是 500（限流的错误状态）
                failedRequests++;
            } else {
                // 如果请求成功
                successfulRequests++;
            }

            // 每次请求后稍微等一下，模拟请求间隔
            Thread.sleep(100); // 100毫秒间隔
        }

        // 断言：成功请求次数应该小于等于 100（每分钟最多 100 次）
        assertTrue(successfulRequests <= 100, "请求成功次数应该小于等于 100 次");

        // 断言：失败请求次数应该至少为 50（超出 100 次限制的请求）
        assertTrue(failedRequests >= 50, "失败请求次数应该至少为 50 次");

        System.out.println("成功的请求次数：" + successfulRequests);
        System.out.println("失败的请求次数：" + failedRequests);
    }
}
