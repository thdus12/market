package com.api.market.domain.payment.model.dto.response;

import com.api.market.domain.payment.type.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentRefundResponse {
    private PaymentStatus status;
}
