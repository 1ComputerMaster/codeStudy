package com.ecommerce.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter //Package 단위로 먹지 않은 이유는 new ModelMapper가 접근을 못하여서
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    @Setter
    private List<ResponseOrder> orders;
}
