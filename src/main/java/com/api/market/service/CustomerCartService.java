package com.api.market.service;

import com.api.market.core.config.error.entity.ApiException;
import com.api.market.core.config.error.entity.ErrorCode;
import com.api.market.domain.common.provider.CustomerProvider;
import com.api.market.domain.customer.entity.Customer;
import com.api.market.domain.customer.entity.CustomerCart;
import com.api.market.domain.customer.model.dto.request.PatchCustomerCartRequest;
import com.api.market.domain.customer.model.dto.request.PostCustomerCartRequest;
import com.api.market.domain.customer.model.dto.response.GetCustomerCartProductResponse;
import com.api.market.domain.customer.model.dto.response.GetCustomerCartResponse;
import com.api.market.domain.customer.repository.CustomerCartRepository;
import com.api.market.domain.customer.type.QuantityType;
import com.api.market.domain.product.entity.Product;
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
    private final ProductService productService;

    private final CustomerCartRepository customerCartRepository;

    // 장바구니 목록 조회
    public GetCustomerCartResponse list() {
        // 상품 목록
        List<GetCustomerCartProductResponse> products = customerCartRepository.search();

        // 총 결제 금액
        Long totalAmount = products.stream()
                .mapToLong(p-> p.getProductPrice() * p.getQuantity())
            .sum();

        return new GetCustomerCartResponse(totalAmount, products);
    }

    // 장바구니 추가
    public Boolean insert(PostCustomerCartRequest dto) {
        Customer customer = customerProvider.getCustomer();
        Product product =  productService.getProduct(dto.getProductId());
        validateProduct(product.getQuantity());

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

    // 장바구니 상품 수량 수정
    public Boolean updateQuantity(Long id, PatchCustomerCartRequest dto) {
        CustomerCart cart = getCustomerCart(id);
        if (!cart.getProduct().getEnabled()) {
            throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND, "존재하지 않는 상품 입니다.");
        }

        validateProduct(cart.getProduct().getQuantity());

        if (dto.getType() == QuantityType.PLUS) {
            cart.incrementQuantity();
        } else {
            cart.decrementQuantity();
        }

        return true;
    }

    // 장바구니 상품 삭제
    public Boolean delete(Long id) {
        CustomerCart cart = getCustomerCart(id);
        customerCartRepository.delete(cart);
        return true;
    }

    // 장바구니 비우기
    public Boolean clear() {
        Customer customer = customerProvider.getCustomer();
        customerCartRepository.deleteAllByCustomer(customer);
        return true;
    }

    // 상품 유효성 검사
    private void validateProduct(Integer quantity) {
        if (quantity == 0) {
            throw new ApiException(ErrorCode.SOLD_OUT, "품절된 상품 입니다.");
        }
    }

    private CustomerCart getCustomerCart(Long id) {
        return customerCartRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.CART_NOT_FOUND, "장바구니 항목을 찾을 수 없습니다."));
    }

    public List<CustomerCart> getCustomerCarts(Customer customer, List<Long> ids) {
        // customer, customerCartIds에 해당하는 장바구니 목록 조회
        List<CustomerCart> carts = customerCartRepository.findAllByCustomerAndIdIn(customer, ids);

        // 모든 ids가 조회되었는지 확인
        if (carts.size() != ids.size()) {
            throw new ApiException(ErrorCode.CART_NOT_FOUND, "장바구니 항목을 찾을 수 없습니다.");
        }

        return carts;
    }
}
