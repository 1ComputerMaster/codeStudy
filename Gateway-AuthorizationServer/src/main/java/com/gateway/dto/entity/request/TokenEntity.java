package com.gateway.dto.entity.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
@Getter
@Builder
@RedisHash("Token")
public class TokenEntity {
    @Id
    private String userID;
    private String accessToken;
    private String refreshToken;
}
