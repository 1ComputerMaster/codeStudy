package com.gateway.adapter.driving;

import com.gateway.port.output.SavePort;
import com.gateway.port.usecase.JwtUsecase;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final JwtUsecase jwtUsecase;
    private final SavePort savePort;
    public AuthorizationHeaderFilter(JwtUsecase jwtUsecase, SavePort savePort){
        super(Config.class);
        this.jwtUsecase = jwtUsecase;
        this.savePort = savePort;
    }

    public static class Config{

    }

    /**
     * @apiNote 1. 일반적인 회원 가입 요청을 제외한 모든 요청은 이곳을 탐험한다. <br/> 
     * 2. 즉, 모든 요청은 이곳의 필터를 지나는데 만일, 이미 accessToken이 존재하지만 또 다른 곳에서 동일한 유저로 요청시 토큰 삭제를 하고
     *<br/>
     * 3. 다시 재 로그인을 할 때는 정상 로그인을 할 수 있는 방식으로 구동시켰다.
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            //JWT 토큰 검증
            if(!jwtUsecase.validateToken(jwt)){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            String username = jwtUsecase.getUserInfo(jwt).get("username");

            savePort.putIfPresent("TOKEN",username,jwt)
                .flatMap(ret -> {
                    if(!ret){
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    }
                    return response.setComplete();
                });
            return chain.filter(exchange);
        };
    }

}
