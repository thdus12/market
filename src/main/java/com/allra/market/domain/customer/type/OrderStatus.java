package com.allra.market.domain.customer.type;

import com.allra.market.domain.common.converter.GenericTypeConverter;
import com.allra.market.domain.common.type.PersistableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus implements PersistableEnum<String> {
    PENDING("결제 대기"),
    PAID("결제 완료"),
    CANCELLED("결제 취소");

    private final String description;

    @Override
    public String getValue() {
        return this.name();
    }

    public static class Converter extends GenericTypeConverter<OrderStatus, String> {
        public Converter() {
            super(OrderStatus.class);
        }
    }
}