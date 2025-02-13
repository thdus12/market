package com.allra.market.service;

import com.allra.market.core.config.error.entity.ApiException;
import com.allra.market.core.config.error.entity.ErrorCode;
import com.allra.market.domain.common.provider.CustomerProvider;
import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.entity.CustomerCart;
import com.allra.market.domain.customer.model.dto.request.PostCustomerCartRequest;
import com.allra.market.domain.customer.model.dto.response.GetCustomerCartProductResponse;
import com.allra.market.domain.customer.model.dto.response.GetCustomerCartResponse;
import com.allra.market.domain.customer.repository.CustomerCartRepository;
import com.allra.market.domain.product.entity.Product;
import com.allra.market.domain.product.repository.ProductRepository;
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
    private final CustomerProvider customerProvider;
    private final CustomerCartRepository customerCartRepository;
    private final ProductRepository productRepository;

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
    public Boolean insert(PostCustomerCartRequest dto) {
        Customer customer = customerProvider.getCustomer();
        Product product = productRepository.findByIdAndEnabledIsTrue(dto.getProductId())
                .orElseThrow(() -> new ApiException(ErrorCode.DATA_NOT_FOUND, "존재하지 않는 상품 입니다."));

        if (product.getQuantity() == 0) {
            throw new ApiException(ErrorCode.SOLD_OUT, "품절된 상품 입니다.");
        }

        // 이미 장바구니에 있는 상품인지 확인
        CustomerCart existingCart = customerCartRepository.findByCustomerAndProduct(customer, product)
                .orElse(null);

        if (existingCart != null) {
            existingCart.incrementQuantity();
        } else {
            CustomerCart cart = new CustomerCart(customer, product, dto.getQuantity());
            customerCartRepository.save(cart);
        }

        return true;
    }
}
