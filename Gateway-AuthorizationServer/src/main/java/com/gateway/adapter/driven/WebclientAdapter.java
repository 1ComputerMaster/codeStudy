package com.gateway.adapter.driven;

import com.gateway.config.APIInfo;
import com.gateway.domain.vo.request.LoginRequest;
import com.gateway.port.output.WebClientPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebclientAdapter implements WebClientPort {
    private final WebClient webClient;
    private final APIInfo apiInfo;

    public WebclientAdapter (APIInfo apiInfo) {
        this.apiInfo = apiInfo;
        this.webClient = WebClient.builder()
                .baseUrl(this.apiInfo.getUser())
                .build();
    }

    /**
     *
     * @param loginRequest
     * @return boolean
     * @apiNote 1. id, pw 값 요청을 유저 서버에 보내서 우선 존재하는 유저인지 검증을 한다.
     * <br/> 2. 만일, id,pw 값에 일치하는 정보가 존재 할 시 return true else return false
     *
     */
    @Override
    public Mono<Boolean> validateUser(LoginRequest loginRequest) {
        return webClient.post()
                .uri("/validateUser")
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
