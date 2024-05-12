package com.ecommerce.user.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createAt;
    private String orderId;
}
