package com.allra.market.domain.payment.type;

import com.allra.market.domain.common.converter.GenericTypeConverter;
import com.allra.market.domain.common.type.PersistableEnum;
import com.allra.market.domain.customer.type.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus implements PersistableEnum<String> {
    PENDING("대기"),
    COMPLETED("완료"),
    FAILED("실패"),
    REFUNDED("환불됨");

    private final String description;

    @Override
    public String getValue() {
        return this.name();
    }

    public static class Converter extends GenericTypeConverter<PaymentStatus, String> {
        public Converter() {
            super(PaymentStatus.class);
        }
    }
}