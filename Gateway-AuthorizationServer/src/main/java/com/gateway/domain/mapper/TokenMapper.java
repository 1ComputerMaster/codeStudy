package com.gateway.domain.mapper;

import com.gateway.domain.entity.request.TokenEntity;
import com.gateway.domain.vo.TokenVO;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public TokenEntity getTokenMapToEntity(TokenVO tokenRequest){
        return TokenEntity.builder()
                .accessToken(tokenRequest.getAccessToken())
                .refreshToken(tokenRequest.getRefreshToken())
                .build();
    }
    public TokenVO getTokenMapToVO(TokenEntity tokenEntity){
        return TokenVO.builder()
                .accessToken(tokenEntity.getAccessToken())
                .refreshToken(tokenEntity.getRefreshToken())
                .build();
    }

}
