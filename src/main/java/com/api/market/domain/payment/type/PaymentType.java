package com.api.market.domain.payment.type;

import com.api.market.domain.common.converter.GenericTypeConverter;
import com.api.market.domain.common.type.PersistableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType implements PersistableEnum<String> {
    CARD("카드"),
    BANK_TRANSFER("계좌 이체"),
    VIRTUAL_ACCOUNT("가상 계좌");

    private final String description;

    @Override
    public String getValue() {
        return this.name();
    }

    public static class Converter extends GenericTypeConverter<PaymentType, String> {
        public Converter() {
            super(PaymentType.class);
        }
    }
}