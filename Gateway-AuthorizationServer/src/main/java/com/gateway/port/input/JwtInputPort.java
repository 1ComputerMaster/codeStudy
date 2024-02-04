package com.gateway.port.input;

import com.gateway.port.usecase.JwtUsecase;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtInputPort implements JwtUsecase {

    // 토큰의 서명 키
    private final String secretKey = "secret";

    // 토큰의 유효 시간 (30분)
    private final long expireTime = 30 * 60 * 1000L;

    // 토큰을 생성하는 메서드
    public String createToken(String userID, String role) {
        // 헤더 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // 페이로드 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", userID);
        payloads.put("role", "ADMIN".equals(role) ? role : "USER");

        // 토큰의 만료 시간 설정
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTime);

        // 토큰 생성
        return Jwts.builder()
                .setHeader(headers) // 헤더 설정
                .setClaims(payloads) // 페이로드 설정
                .setExpiration(expiration) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 알고리즘과 키 설정
                .compact(); // 토큰 생성
    }

    // 토큰의 유효성을 검사하는 메서드
    public boolean validateToken(String token) {
        try {
            // 토큰의 서명과 만료 시간을 검사
            Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true; // 유효한 토큰
        } catch (Exception e) {
            // 잘못된 서명, 만료된 토큰, 지원되지 않는 토큰 형식 등의 예외 처리
            e.printStackTrace();
            return false; // 유효하지 않은 토큰
        }
    }

    // 토큰에서 사용자 정보를 추출하는 메서드
    public Map<String, Object> getUserInfo(String token) {
        // 토큰에서 페이로드를 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody(); // 페이로드 추출

        // 페이로드에서 사용자 정보를 추출
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", claims.get("username"));
        userInfo.put("role", claims.get("role"));

        return userInfo; // 사용자 정보 반환
    }
}