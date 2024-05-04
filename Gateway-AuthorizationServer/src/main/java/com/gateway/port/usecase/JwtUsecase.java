package com.gateway.port.usecase;

import com.gateway.domain.vo.TokenVO;

import java.util.Map;

public interface JwtUsecase {
    public TokenVO createToken(String userID, String role);
    public boolean validateToken(String token);

    public Map<String, String> getUserInfo(String token);
}