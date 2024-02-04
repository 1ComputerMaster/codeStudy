package com.gateway.adapter.driving;

import com.gateway.port.usecase.JwtUsecase;
import com.gateway.vo.request.LoginRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class APIDrivingAdapter {

    private final JwtUsecase jwtUsecase;

    private final APIInfo apiInfo;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("login_route", r -> r.path("/user/login")
                        .filters(f ->
                             f.modifyRequestBody(LoginRequest.class, String.class, MediaType.APPLICATION_JSON_VALUE,
                                    (exchange, loginRequest) -> {
                                        if (true/*Cacheable의 결과로 체크 할 것임*/) {
                                            String token = jwtUsecase.createToken(loginRequest.getUserID(), "USER");
                                            return Mono.just(token);
                                        } else {
                                            return Mono.error(new RuntimeException("Invalid credentials"));
                                        }
                                    })
                        )
                        .uri(apiInfo.getUser()))
                .route("UserService", r -> r.path("/user/*")
                        .filters(f ->  f.filter((exchange,chain) -> {
                            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
                            /*Cacheable 후 만일 결과 값이 비인가 토큰 이면 Mono.error 리턴*/
                            if(jwtUsecase.validateToken(token)){
                                return chain.filter(exchange);
                            }else{
                                return Mono.error(new RuntimeException("Invalid credentials"));
                            }
                        }))
                        .uri(apiInfo.getUser()))
                .route("ProductService", r -> r.path("/product/*")
                        .filters(f ->  f.filter((exchange,chain) -> {
                            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
                            Map<String, Object> userInfo = jwtUsecase.getUserInfo(token);
                            return chain.filter(exchange);
                        }))
                        .uri(apiInfo.getProduct()))
                .build();

    }
}

@Getter
@Configuration
@ConfigurationProperties("http.api")
class APIInfo {
    private String user;
    private String product;
}