package com.allra.market.domain.customer.model.dto.response;

import com.allra.market.domain.customer.type.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCustomerOrderResponse {
    private Long id;
    private Long totalAmount;
    private OrderStatus status;
    private List<GetCustomerOrderProductResponse> products;

    @QueryProjection
    public GetCustomerOrderResponse(Long id, Long totalAmount, OrderStatus status, List<GetCustomerOrderProductResponse> products) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.products = products;
    }
}
