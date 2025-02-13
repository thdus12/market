package com.allra.market.service;

import com.allra.market.domain.customer.model.dto.response.GetCustomerCartProductResponse;
import com.allra.market.domain.customer.model.dto.response.GetCustomerCartResponse;
import com.allra.market.domain.customer.repository.CustomerCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerCartService {
    private final CustomerCartRepository customerCartRepository;

    // 장바구니 목록 조회
    public GetCustomerCartResponse list() {
        // 상품 목록
        List<GetCustomerCartProductResponse> products = customerCartRepository.search();

        // 총 결제 금액
        Integer totalAmount = Math.toIntExact(products.stream()
                .mapToLong(p-> p.getProductPrice() * p.getQuantity())
                .sum());

        return new GetCustomerCartResponse(totalAmount, products);
    }

    // 장바구니 추가

    // 장바구니 상품 삭제

    // 장바구니 상품 수량 수정

    // 장바구니 비우기

}
