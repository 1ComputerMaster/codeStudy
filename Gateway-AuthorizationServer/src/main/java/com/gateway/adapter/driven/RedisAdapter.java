package com.gateway.adapter.driven;

import com.gateway.dto.entity.request.TokenEntity;
import com.gateway.port.output.SavePort;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RedisAdapter implements SavePort {

    private ReactiveHashOperations<String,String, Object> redisOperations;
    public RedisAdapter(ReactiveHashOperations<String,String, Object> redisOperations) {
        this.redisOperations = redisOperations;
    }
    @Override
    public Mono<Boolean> save(TokenEntity tokenEntity) {
        return redisOperations.put("TOKEN",UUID.randomUUID().toString(), tokenEntity);
    }

    public Mono<Boolean> putIfPresent(String hash, String key, String value) {
        return redisOperations.hasKey(hash, key)
                .flatMap(present -> {
                    if (present) {
                        redisOperations.remove(hash,key);
                        return Mono.just(false);
                    } else {
                        return redisOperations.put(hash, key, value);
                    }
                });
    }

}
