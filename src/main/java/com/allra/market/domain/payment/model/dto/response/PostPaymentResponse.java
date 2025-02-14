package com.allra.market.domain.payment.model.dto.response;

import com.allra.market.domain.payment.type.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentResponse {
    private PaymentStatus status;
    private String transactionId;
}
