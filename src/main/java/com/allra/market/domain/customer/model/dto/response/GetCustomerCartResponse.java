package com.allra.market.domain.customer.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCustomerCartResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private boolean soldOut;

    @QueryProjection
    public GetCustomerCartResponse(Long productId, String productName, Integer productQuantity, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.soldOut = productQuantity > 0;
    }
}
