package com.allra.market.domain.common.converter;

import com.allra.market.domain.common.type.PersistableEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class GenericTypeConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> cls;

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        if (dbData == null) {
            return null;
        }

        T[] enums = cls.getEnumConstants();
        if (enums != null) {
            for (T e : enums) {
                if (e.getValue().equals(dbData)) {
                    return e;
                }
            }
        }
        throw new UnsupportedOperationException(dbData.toString());
    }
}
