package com.gateway.dto.vo.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRequest {
    private String accessToken;
    private String refreshToken;
}
