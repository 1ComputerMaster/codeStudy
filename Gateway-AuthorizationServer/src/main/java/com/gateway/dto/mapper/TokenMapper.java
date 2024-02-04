package com.gateway.dto.mapper;

import com.gateway.dto.entity.request.TokenEntity;
import com.gateway.dto.vo.request.TokenRequest;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public TokenEntity getTokenMapToEntity(TokenRequest tokenRequest){
        return TokenEntity.builder()
                .accessToken(tokenRequest.getAccessToken())
                .refreshToken(tokenRequest.getRefreshToken())
                .build();
    }
    public TokenRequest getTokenMapToVO(TokenEntity tokenEntity){
        return TokenRequest.builder()
                .accessToken(tokenEntity.getAccessToken())
                .refreshToken(tokenEntity.getRefreshToken())
                .build();
    }

}
