package com.api.market.domain.customer.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCustomerCartProductResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Long productPrice;

    @QueryProjection
    public GetCustomerCartProductResponse(Long id, Integer quantity, Long productId, String productName, Long productPrice) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
