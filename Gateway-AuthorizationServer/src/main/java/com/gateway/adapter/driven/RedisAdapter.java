package com.gateway.adapter.driven;

import com.gateway.dto.entity.request.TokenEntity;
import com.gateway.port.output.SavePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RedisAdapter implements SavePort {

    private ReactiveRedisOperations<String, Object> redisOperations;
    public RedisAdapter(ReactiveRedisOperations<String, Object> redisOperations) {
        this.redisOperations = redisOperations;
    }
    @Override
    public Mono<Boolean> save(TokenEntity tokenEntity) {
        return redisOperations.opsForValue().set("Token", tokenEntity);
    }
}
