package com.gateway.port.usecase;

import com.gateway.dto.vo.TokenVO;

import java.util.Map;

public interface JwtUsecase {
    public TokenVO createToken(String userID, String role);
    public boolean validateToken(String token);

    public Map<String, String> getUserInfo(String token);
}