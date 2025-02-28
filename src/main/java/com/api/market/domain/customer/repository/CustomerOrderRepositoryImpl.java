package com.api.market.domain.customer.repository;

import com.api.market.domain.customer.entity.CustomerOrder;
import com.api.market.domain.customer.model.dto.request.GetCustomerOrderRequest;
import com.api.market.domain.customer.model.dto.response.GetCustomerOrderProductResponse;
import com.api.market.domain.customer.model.dto.response.GetCustomerOrderResponse;
import com.api.market.domain.customer.type.OrderStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.api.market.domain.customer.entity.QCustomerOrder.customerOrder;
import static com.api.market.domain.customer.entity.QCustomerOrderProduct.customerOrderProduct;
import static com.api.market.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class CustomerOrderRepositoryImpl implements CustomerOrderRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetCustomerOrderResponse> search(Long customerId, GetCustomerOrderRequest dto) {
        List<CustomerOrder> orders = queryFactory
            .selectFrom(customerOrder)
            .leftJoin(customerOrder.products, customerOrderProduct).fetchJoin()
            .leftJoin(customerOrderProduct.product, product).fetchJoin()
            .where(searchFilter(dto, customerId))
            .orderBy(customerOrder.createdDate.desc())
            .distinct() // fetch join으로 인한 중복 데이터 제거
            .fetch();

        return orders.stream()
            .map(order -> new GetCustomerOrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getProducts().stream()
                    .map(orderProduct -> new GetCustomerOrderProductResponse(
                        orderProduct.getProduct().getId(),
                        orderProduct.getProduct().getName(),
                        orderProduct.getPrice(),
                        orderProduct.getQuantity()
                    ))
                    .toList()
            ))
            .toList();
    }

    private Predicate searchFilter(GetCustomerOrderRequest dto, Long customerId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(customerOrder.customer.id.eq(customerId));

        // 등록일 기준 조회
        builder.and(createdDateBetween(dto.getStartDate(), dto.getEndDate()));
        // 주문 상태
        builder.and(orderStatusEq(dto.getStatus()));

        return builder;
    }

    private BooleanBuilder createdDateBetween(LocalDate startDate, LocalDate endDate) {
        return dateBetween(customerOrder.createdDate, startDate, endDate);
    }

    private BooleanBuilder orderStatusEq(OrderStatus status) {
        return status != null ? new BooleanBuilder().and(customerOrder.status.eq(status)) : null;
    }

    private static BooleanBuilder dateBetween(DateTimePath<LocalDateTime> date, LocalDate startDate, LocalDate endDate) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(startDate != null ? date.goe(startDate.atStartOfDay()) : null);
        builder.and(endDate != null ? date.lt(endDate.atStartOfDay().plusDays(1)) : null);
        return builder;
    }
}
