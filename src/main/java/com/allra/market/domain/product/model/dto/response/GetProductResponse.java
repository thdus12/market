package com.allra.market.domain.product.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductResponse {
    // 고유 id
    private Long id;
    // 상품명
    private String name;
    // 결제 금액
    private Long price;

    @QueryProjection
    public GetProductResponse(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
