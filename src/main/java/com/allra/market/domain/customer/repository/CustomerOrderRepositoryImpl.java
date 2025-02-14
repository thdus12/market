package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderProductResponse;
import com.allra.market.domain.customer.model.dto.response.QGetCustomerOrderProductResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.allra.market.domain.customer.entity.QCustomerOrderProduct.customerOrderProduct;
import static com.allra.market.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class CustomerOrderRepositoryImpl implements CustomerOrderRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetCustomerOrderProductResponse> search(Long customerOrderId) {
        return queryFactory
                .select(new QGetCustomerOrderProductResponse(
                        product.id,
                        product.name,
                        customerOrderProduct.price,
                        customerOrderProduct.quantity
                ))
                .from(customerOrderProduct)
                .leftJoin(customerOrderProduct.product, product)
                .where(customerOrderProduct.order.id.eq(customerOrderId))
                .fetch();
    }
}
