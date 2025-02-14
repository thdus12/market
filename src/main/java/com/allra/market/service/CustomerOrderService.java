package com.allra.market.service;

import com.allra.market.core.config.error.entity.ApiException;
import com.allra.market.core.config.error.entity.ErrorCode;
import com.allra.market.domain.common.provider.CustomerProvider;
import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.entity.CustomerCart;
import com.allra.market.domain.customer.entity.CustomerOrder;
import com.allra.market.domain.customer.entity.CustomerOrderProduct;
import com.allra.market.domain.customer.model.dto.request.PostCustomerOrderCartPaymentRequest;
import com.allra.market.domain.customer.model.dto.request.PostCustomerOrderPaymentRequest;
import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderProductResponse;
import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderResponse;
import com.allra.market.domain.customer.repository.CustomerOrderRepository;
import com.allra.market.domain.customer.type.OrderStatus;
import com.allra.market.domain.payment.client.PaymentClient;
import com.allra.market.domain.payment.entity.Payment;
import com.allra.market.domain.payment.model.dto.request.PostPaymentRequest;
import com.allra.market.domain.payment.model.dto.response.PostPaymentResponse;
import com.allra.market.domain.payment.repository.PaymentRepository;
import com.allra.market.domain.payment.type.PaymentStatus;
import com.allra.market.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerOrderService {
    private final PaymentClient client;
    private final CustomerProvider customerProvider;
    private final ProductService productService;
    private final CustomerCartService customerCartService;

    private final CustomerOrderRepository customerOrderRepository;
    private final PaymentRepository paymentRepository;

    // 바로 구매
    public Boolean direct(PostCustomerOrderPaymentRequest dto) {
        Customer customer = customerProvider.getCustomer();

        // 상품 유효성 검사
        Product product = productService.findProductWithLock(dto.getProductId());
        validateProduct(product, dto.getQuantity());

        // 총 결제 금액
        Long totalAmount = product.getPrice() * dto.getQuantity();

        // 주문 생성
        CustomerOrder customerOrder = new CustomerOrder(customer, OrderStatus.PENDING, totalAmount);
        CustomerOrderProduct orderProduct = new CustomerOrderProduct(customerOrder, product, dto.getQuantity());
        customerOrder.addProduct(orderProduct);
        customerOrderRepository.save(customerOrder);

        try {
            // 결제 진행
            PostPaymentResponse response = client.payment(new PostPaymentRequest(dto.getType(), totalAmount));
            Payment payment = new Payment(customerOrder, dto.getType());
            paymentRepository.save(payment);

            if (response.getStatus() == PaymentStatus.COMPLETED) {
                // 결제 성공
                payment.approve(response.getTransactionId());
                product.decreaseStock(dto.getQuantity());
                return true;
            } else if (response.getStatus() == PaymentStatus.PENDING){
                // 결제 대기
                return true;
            } else {
                // 결제 실패
                payment.fail();
                throw new ApiException(ErrorCode.PAYMENT_FAILED, "결제에 실패했습니다.");
            }

        } catch (Exception e) {
            log.error("주문 처리 중 오류 발생", e);
            throw new ApiException(ErrorCode.ORDER_FAILED, "주문 처리 중 오류가 발생했습니다.");
        }
    }

    // 장바구니 구매
    public Boolean cart(PostCustomerOrderCartPaymentRequest dto) {
        Customer customer = customerProvider.getCustomer();
        List<CustomerCart> carts = customerCartService.getCustomerCarts(customer);

        if (carts.isEmpty()) {
            throw new ApiException(ErrorCode.DATA_NOT_FOUND, "장바구니가 비어있습니다.");
        }

        // 총 결제 금액
        Long totalAmount = carts.stream()
                .mapToLong(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();

        // 주문 생성
        CustomerOrder customerOrder = new CustomerOrder(customer, OrderStatus.PENDING, totalAmount);
        for (CustomerCart cart : carts) {
            Product product = productService.findProductWithLock(cart.getProduct().getId());
            validateProduct(product, cart.getQuantity());

            CustomerOrderProduct orderProduct = new CustomerOrderProduct(customerOrder, product, cart.getQuantity());
            customerOrder.addProduct(orderProduct);
        }
        customerOrderRepository.save(customerOrder);

        try {
            // 결제 진행
            PostPaymentResponse response = client.payment(new PostPaymentRequest(dto.getType(), totalAmount));
            Payment payment = new Payment(customerOrder, dto.getType());
            paymentRepository.save(payment);

            if (response.getStatus() == PaymentStatus.COMPLETED) {
                // 결제 성공
                payment.approve(response.getTransactionId());
                // 상품 재고 감소
                carts.forEach(c -> c.getProduct().decreaseStock(c.getQuantity()));
                // 장바구니 비우기
                customerCartService.clear();
                return true;
            } else if (response.getStatus() == PaymentStatus.PENDING){
                // 결제 대기
                return true;
            } else {
                // 결제 실패
                payment.fail();
                throw new ApiException(ErrorCode.PAYMENT_FAILED, "결제에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("주문 처리 중 오류 발생", e);
            throw new ApiException(ErrorCode.ORDER_FAILED, "주문 처리 중 오류가 발생했습니다.");
        }
    }

    private void validateProduct(Product product, Integer quantity) {
        if (product.getQuantity() < quantity) {
            throw new ApiException(ErrorCode.OUT_OF_STOCK, product.getId());
        }
        if (product.getQuantity() == 0) {
            throw new ApiException(ErrorCode.SOLD_OUT, product.getId());
        }
    }
}
