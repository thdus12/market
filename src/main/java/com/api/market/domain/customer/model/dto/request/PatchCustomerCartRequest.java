package com.api.market.domain.customer.model.dto.request;

import com.api.market.domain.customer.type.QuantityType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchCustomerCartRequest {
    @NotNull
    private QuantityType type;
}
