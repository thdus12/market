package com.api.market.domain.payment.type;

import com.api.market.domain.common.converter.GenericTypeConverter;
import com.api.market.domain.common.type.PersistableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus implements PersistableEnum<String> {
    PENDING("대기"),
    COMPLETED("완료"),
    FAILED("실패")
    ;

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