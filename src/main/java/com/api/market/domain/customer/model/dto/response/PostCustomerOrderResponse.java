package com.api.market.domain.customer.model.dto.response;

import com.api.market.domain.payment.type.PaymentStatus;
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
