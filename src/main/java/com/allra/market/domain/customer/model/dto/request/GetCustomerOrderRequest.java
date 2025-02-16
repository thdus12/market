package com.allra.market.domain.customer.model.dto.request;

import com.allra.market.domain.customer.type.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetCustomerOrderRequest {
    // 시작일 (등록일 검색)
    private LocalDate startDate;
    // 종료일 (등록일 검색)
    private LocalDate endDate;
    // 주문 상태
    private OrderStatus status;
}
