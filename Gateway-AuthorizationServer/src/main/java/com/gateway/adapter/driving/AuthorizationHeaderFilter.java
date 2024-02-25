package com.gateway.adapter.driving;

import com.gateway.dto.mapper.TokenMapper;
import com.gateway.dto.vo.TokenVO;
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


@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final JwtUsecase jwtUsecase;
    private final SavePort savePort;
    private final TokenMapper tokenMapper;

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";

    public AuthorizationHeaderFilter(JwtUsecase jwtUsecase, SavePort savePort, TokenMapper tokenMapper){
        super(Config.class);
        this.jwtUsecase = jwtUsecase;
        this.savePort = savePort;
        this.tokenMapper = tokenMapper;
    }

    /**
     * SCG의 application.yaml 중 Config 값을 가져와서 사용하기 위한 건인데 잘 안 써서 아무것도 사용하지 않았습니다.
     */
    public static class Config{

    }

    /**
     *
     * @apiNote
     * <pre>
     * 1. 일반적인 회원 가입 요청을 제외한 모든 요청은 이곳을 탐험한다.
     * 2. 즉, 모든 요청은 이곳의 필터를 지나는데 만일, 이미 accessToken이 존재하지만 또 다른 곳에서 동일한 유저로 요청시 토큰 삭제를 하고
     * 3. 다시 재 로그인을 할 때는 정상 로그인을 할 수 있는 방식으로 구동시켰다.
     * </pre>
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

            String authorizationHeader = request.getHeaders().get(ACCESS_TOKEN).get(0);
            String refreshAuthorizationHeader = request.getHeaders().get(REFRESH_TOKEN).get(0);

            String accessJwt = authorizationHeader.replace("Bearer", "");
            String refreshJwt = refreshAuthorizationHeader.replace("Bearer", "");

            //JWT 토큰 검증
            if(!jwtUsecase.validateToken(accessJwt)){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            String username = jwtUsecase.getUserInfo(accessJwt).get("username");

            TokenVO tokenVO = TokenVO.builder()
                    .accessToken(accessJwt)
                    .refreshToken(refreshJwt)
                    .build();

            savePort.putIfPresent("TOKEN",username, tokenMapper.getTokenMapToEntity(tokenVO))
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
