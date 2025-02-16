package com.allra.market.domain.customer.model.dto.response;

import com.allra.market.domain.payment.type.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCustomerOrderResponse {
    private PaymentStatus status;

    public PostCustomerOrderResponse(PaymentStatus status) {
        this.status = status;
    }
}
