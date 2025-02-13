package com.allra.market.domain.product.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutProductRequest {
    // 상품명
    @NotBlank
    private String name;

    // 설명
    @NotBlank
    private String description;

    // 결제 금액
    @NotNull
    private Long price;

    // 수량
    @NotNull
    private Integer quantity;
}
