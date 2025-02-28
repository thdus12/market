package com.api.market.domain.product.repository;

import com.api.market.domain.product.model.dto.request.GetProductRequest;
import com.api.market.domain.product.model.dto.response.GetProductResponse;
import com.api.market.domain.product.model.dto.response.QGetProductResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.api.market.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    private JPAQuery<?> searchFactory(GetProductRequest dto) {
        return queryFactory
            .from(product)
            .where(searchFilter(dto));
    }

    @Override
    public Page<GetProductResponse> search(GetProductRequest dto, Pageable pageable) {
        Long total = searchFactory(dto)
            .select(Wildcard.count)
            .fetch()
            .get(0);

        List<GetProductResponse> result = total > 0 ? searchFactory(dto)
            .select(new QGetProductResponse(
                product.id,
                product.name,
                product.price
            ))
            .orderBy(product.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch() : new ArrayList<>();

        return new PageImpl<>(result, pageable, total);
    }

    private Predicate searchFilter(GetProductRequest dto) {
        BooleanBuilder builder = new BooleanBuilder();
        // 활성화 된 상품만
        builder.and(product.enabled.eq(true));
        // 품절된 상품 제외
        builder.and(product.quantity.gt(0));

        // 등록일 기준 조회
        builder.and(createdDateBetween(dto.getStartDate(), dto.getEndDate()));

        // 검색어가 존재 한다면
        if (dto.getSearchText() != null && !dto.getSearchText().isBlank()) {
            BooleanBuilder orBuilder = new BooleanBuilder();
            orBuilder.or(nameContain(dto.getSearchText()));
            orBuilder.or(descriptionContain(dto.getSearchText()));
            builder.and(orBuilder);
        }

        return builder;
    }

    private BooleanExpression nameContain(String value) {
        return value != null ? product.name.contains(value) : null;
    }

    private BooleanExpression descriptionContain(String value) {
        return value != null ? product.description.containsIgnoreCase(StringEscapeUtils.escapeHtml4(value)) : null;
    }

    private BooleanBuilder createdDateBetween(LocalDate startDate, LocalDate endDate) {
        return dateBetween(product.createdDate, startDate, endDate);
    }

    private static BooleanBuilder dateBetween(DateTimePath<LocalDateTime> date, LocalDate startDate, LocalDate endDate) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(startDate != null ? date.goe(startDate.atStartOfDay()) : null);
        builder.and(endDate != null ? date.lt(endDate.atStartOfDay().plusDays(1)) : null);
        return builder;
    }
}
