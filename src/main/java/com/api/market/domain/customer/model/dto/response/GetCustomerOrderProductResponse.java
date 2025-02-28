package com.api.market.domain.customer.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCustomerOrderProductResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private Long price;

    @QueryProjection
    public GetCustomerOrderProductResponse(Long id, String name, Long price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
