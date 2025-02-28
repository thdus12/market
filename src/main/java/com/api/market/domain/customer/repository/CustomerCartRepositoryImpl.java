package com.api.market.domain.customer.repository;

import com.api.market.domain.customer.model.dto.response.GetCustomerCartProductResponse;
import com.api.market.domain.customer.model.dto.response.QGetCustomerCartProductResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.market.domain.customer.entity.QCustomerCart.customerCart;
import static com.api.market.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class CustomerCartRepositoryImpl implements CustomerCartRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetCustomerCartProductResponse> search() {
        return queryFactory
            .select(new QGetCustomerCartProductResponse(
                customerCart.id,
                customerCart.quantity,
                product.id,
                product.name,
                product.price
            ))
            .from(customerCart)
            .leftJoin(customerCart.customer)
            .leftJoin(customerCart.product, product)
            .where(searchFilter())
            .orderBy(customerCart.createdDate.desc())
            .fetch();
    }

    private Predicate searchFilter() {
        BooleanBuilder builder = new BooleanBuilder();
        // 삭제 여부
        builder.and(product.enabled.eq(true));
        // 품절 여부
        builder.and(product.quantity.gt(0));

        return builder;
    }
}
