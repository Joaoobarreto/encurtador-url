package com.projetos.encurtador_url.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContadorService {
    private static final String KEY = "link:counter";
    private static final Long INITIAL_VALUE = 2_000_000L;

    private final StringRedisTemplate redisTemplate;

    public ContadorService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long getNext() {
        Boolean exists = redisTemplate.hasKey(KEY);

        if (Boolean.FALSE.equals(exists)) {
            redisTemplate.opsForValue().setIfAbsent(KEY, String.valueOf(INITIAL_VALUE));
        }

        return redisTemplate.opsForValue().increment(KEY);
    }
}
