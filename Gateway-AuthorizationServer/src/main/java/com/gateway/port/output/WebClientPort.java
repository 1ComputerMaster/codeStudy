package com.gateway.port.output;

import com.gateway.dto.vo.request.LoginRequest;
import reactor.core.publisher.Mono;

public interface WebClientPort {
    public Mono<Boolean> validateUser(LoginRequest loginRequest);
}
