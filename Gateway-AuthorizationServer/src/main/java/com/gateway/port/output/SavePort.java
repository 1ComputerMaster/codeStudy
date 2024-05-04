package com.gateway.port.output;

import com.gateway.domain.entity.request.TokenEntity;
import reactor.core.publisher.Mono;

public interface SavePort {
    public Mono<Boolean> save(TokenEntity tokenEntity);
    public Mono<Boolean> putIfPresent(String hash, String key, TokenEntity tokenEntity);
}
