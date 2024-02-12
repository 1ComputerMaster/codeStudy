package com.gateway.dto.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenVO {
    private String accessToken;
    private String refreshToken;
}
