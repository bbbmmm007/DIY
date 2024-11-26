package com.zhny.computer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final int ADD_CARTS_LIMIT = 5; // 每秒最大 5 次
    private static final int PRODUCT_DETAIL_LIMIT = 100; // 每分钟最大 100 次
    private static final int USER_LOGIN_LIMIT = 50; // 每小时最大 50 次

    // 限流逻辑
    public boolean isAllowed(String apiName, String key, int limit, String timeUnit) {
        String rateLimitKey = "rate_limit:" + apiName + ":" + key + ":" + timeUnit;

        long currentTime = System.currentTimeMillis();
        long timePeriod = currentTime / getTimeUnit(timeUnit); // 根据时间单位分组（秒、分钟、小时）

        // 获取当前请求计数
        String requestCount = redisTemplate.opsForValue().get(rateLimitKey + ":" + timePeriod);
        if (requestCount == null) {
            // 如果没有记录，初始化计数
            redisTemplate.opsForValue().set(rateLimitKey + ":" + timePeriod, "1", Duration.ofMillis(getTimeUnit(timeUnit)));
            return true;
        }

        int count = Integer.parseInt(requestCount);
        if (count >= limit) {
            // 超过限制，拒绝请求
            return false;
        } else {
            // 请求未超限，计数 +1
            redisTemplate.opsForValue().increment(rateLimitKey + ":" + timePeriod, 1);
            return true;
        }
    }

    private long getTimeUnit(String timeUnit) {
        switch (timeUnit) {
            case "second": return 1000L;
            case "minute": return 60000L;
            case "hourly": return 3600000L;
            default: return 1000L; // 默认按秒
        }
    }
}
