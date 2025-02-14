package com.allra.market.domain.customer.model.dto.request;

import com.allra.market.domain.payment.type.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostCustomerOrderCartPaymentRequest {
    @NotNull
    private List<Long> customerCartIds;

    @NotNull
    private PaymentType type;
}
