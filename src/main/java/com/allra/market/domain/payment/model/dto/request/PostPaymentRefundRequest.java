package com.allra.market.domain.payment.model.dto.request;

import com.allra.market.domain.payment.type.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentRefundRequest {
    private PaymentType type;
    private Long totalAmount;
    private String transactionId;
}
