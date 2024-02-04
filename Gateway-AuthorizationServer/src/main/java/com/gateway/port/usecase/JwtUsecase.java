package com.gateway.port.usecase;

import java.util.Map;

public interface JwtUsecase {
    public String createToken(String userID, String role);
    public boolean validateToken(String token);

    public Map<String, Object> getUserInfo(String token);
}