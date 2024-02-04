package com.gateway.port.output;

import com.gateway.dto.entity.request.TokenEntity;
import reactor.core.publisher.Mono;

public interface SavePort {
    public Mono<Boolean> save(TokenEntity tokenEntity);
}
