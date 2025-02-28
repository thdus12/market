package com.api.market.domain.product.model.dto.response;

import com.api.market.domain.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductDetailResponse {
    // 상품명
    private String name;
    // 설명
    private String description;
    // 결제 금액
    private Long price;
    // 수량
    private Integer quantity;

    public GetProductDetailResponse(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }
}
