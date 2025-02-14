package com.allra.market.domain.customer.model.dto.request;

import com.allra.market.domain.payment.type.PaymentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCustomerOrderPaymentRequest {
    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private PaymentType type;
}
