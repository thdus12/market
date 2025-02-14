package com.allra.market.domain.customer.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCustomerCartResponse {
    private Long totalAmount;
    private List<GetCustomerCartProductResponse> products;

    public GetCustomerCartResponse(Long totalAmount, List<GetCustomerCartProductResponse> products) {
        this.totalAmount = totalAmount;
        this.products = products;
    }
}
