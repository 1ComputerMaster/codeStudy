package com.ecommerce.catalog.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
@Builder
@Getter
public class CatalogDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
