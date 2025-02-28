package com.api.market.domain.product.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetProductRequest {
    // 시작일 (등록일 검색)
    private LocalDate startDate;
    // 종료일 (등록일 검색)
    private LocalDate endDate;
    // 검색어 (상품명 / 상품 설명)
    private String searchText;
}
