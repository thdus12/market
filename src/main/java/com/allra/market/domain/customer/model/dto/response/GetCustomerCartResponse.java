package com.allra.market.domain.customer.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCustomerCartResponse {
    private Integer totalAmount;
    private List<GetCustomerCartProductResponse> products;

    public GetCustomerCartResponse(Integer totalQuantity, List<GetCustomerCartProductResponse> products) {
        this.totalAmount = totalQuantity;
        this.products = products;
    }
}
