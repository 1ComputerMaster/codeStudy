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
    private JwtUsecase jwtUsecase;
    private SavePort savePort;
    public AuthorizationHeaderFilter(JwtUsecase jwtUsecase, SavePort savePort){
        super(Config.class);
        this.jwtUsecase = jwtUsecase;
        this.savePort = savePort;
    }

    public static class Config{

    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No Authrization Header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            //JWT 토큰 검증
            if(!jwtUsecase.validateToken(jwt)){
                return onError(exchange, "Token Is not Valid", HttpStatus.UNAUTHORIZED);
            }
            String username = jwtUsecase.getUserInfo(jwt).get("username");

            savePort.putIfPresent("TOKEN",username,jwt)
                .flatMap(ret -> {
                    if(!ret){
                        return onError(exchange,"SECOND USER IS COMMING", HttpStatus.UNAUTHORIZED);
                    }
                    return Mono.just(true);
                });
            return chain.filter(exchange);
        };
    }

    //에러 처리 담당
    //Mono : Spring MVC -> Spring WebFlux에서 사용하는 비동기식 데이터 처리타입(Mono:단일, Flux:복수)
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }


}
