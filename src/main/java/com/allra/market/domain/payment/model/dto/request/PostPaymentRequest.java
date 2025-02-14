package com.allra.market.domain.payment.model.dto.request;

import com.allra.market.domain.payment.type.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentRequest {
    private PaymentType type;
    private Long totalAmount;

    public PostPaymentRequest(PaymentType type, Long totalAmount) {
        this.type = type;
        this.totalAmount = totalAmount;
    }
}
