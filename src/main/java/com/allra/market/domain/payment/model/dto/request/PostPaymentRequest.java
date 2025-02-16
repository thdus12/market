package com.allra.market.domain.payment.model.dto.request;

import com.allra.market.domain.payment.type.PaymentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentRequest {
    @NotNull
    private PaymentType type;

    @NotNull
    @Min(1)
    private Long totalAmount;

    public PostPaymentRequest(PaymentType type, Long totalAmount) {
        this.type = type;
        this.totalAmount = totalAmount;
    }
}
