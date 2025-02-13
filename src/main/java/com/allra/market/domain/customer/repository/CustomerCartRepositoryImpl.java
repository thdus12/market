package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.model.dto.response.GetCustomerCartResponse;
import com.allra.market.domain.customer.model.dto.response.QGetCustomerCartResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.allra.market.domain.customer.entity.QCustomerCart.customerCart;
import static com.allra.market.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class CustomerCartRepositoryImpl implements CustomerCartRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<GetCustomerCartResponse> search() {
        return queryFactory
            .select(new QGetCustomerCartResponse(
                product.id,
                product.name,
                product.quantity,
                customerCart.quantity
            ))
            .from(customerCart)
            .leftJoin(customerCart.customer)
            .leftJoin(customerCart.product, product)
            .orderBy(customerCart.createdDate.desc())
            .fetch();
    }
}
