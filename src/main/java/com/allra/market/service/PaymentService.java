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
import com.allra.market.domain.customer.model.dto.response.PostCustomerOrderResponse;
import com.allra.market.domain.customer.type.OrderStatus;
import com.allra.market.domain.payment.client.PaymentClient;
import com.allra.market.domain.payment.entity.Payment;
import com.allra.market.domain.payment.model.dto.request.PostPaymentRequest;
import com.allra.market.domain.payment.model.dto.response.PostPaymentResponse;
import com.allra.market.domain.payment.repository.PaymentRepository;
import com.allra.market.domain.payment.type.PaymentStatus;
import com.allra.market.domain.payment.type.PaymentType;
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
public class PaymentService {
    private final PaymentClient client;
    private final CustomerProvider customerProvider;
    private final ProductService productService;
    private final CustomerCartService customerCartService;
    private final CustomerOrderService customerOrderService;

    private final PaymentRepository paymentRepository;

    // 바로 구매
    public PostCustomerOrderResponse purchase(PostCustomerOrderPaymentRequest dto) {
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
        customerOrderService.save(customerOrder);

        PaymentStatus status = processPayment(customerOrder, dto.getType());
        // 결제 성공일 때 재고 차감
        if (status == PaymentStatus.COMPLETED) {
            product.decreaseStock(dto.getQuantity());
        }

        return new PostCustomerOrderResponse(status);
    }

    // 장바구니 구매
    public PostCustomerOrderResponse cartPurchase(PostCustomerOrderCartPaymentRequest dto) {
        Customer customer = customerProvider.getCustomer();
        List<CustomerCart> carts = customerCartService.getCustomerCarts(customer);

        if (carts.isEmpty()) {
            throw new ApiException(ErrorCode.CART_EMPTY, "장바구니가 비어있습니다.");
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
        customerOrderService.save(customerOrder);

        PaymentStatus status = processPayment(customerOrder, dto.getType());
        // 결제 성공일 때 재고 차감
        if (status == PaymentStatus.COMPLETED) {
            carts.forEach(c -> c.getProduct().decreaseStock(c.getQuantity()));
        }

        return new PostCustomerOrderResponse(status);
    }

    private PaymentStatus processPayment(CustomerOrder customerOrder, PaymentType paymentType) {
        try {
            // 결제 요청
            PostPaymentResponse response = client.payment(
                    new PostPaymentRequest(paymentType, customerOrder.getTotalAmount())
            );

            // 결제 정보 저장
            Payment payment = new Payment(customerOrder, paymentType);
            paymentRepository.save(payment);

            // 결제 상태에 따른 처리
            if (response.getStatus() == PaymentStatus.COMPLETED) {
                payment.approve(response.getTransactionId());
                return PaymentStatus.COMPLETED;
            } else if (response.getStatus() == PaymentStatus.PENDING) {
                return PaymentStatus.PENDING;
            } else {
                payment.fail();
                throw new ApiException(ErrorCode.PAYMENT_FAILED, "결제에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("결제 처리 중 오류 발생", e);
            throw new ApiException(ErrorCode.PAYMENT_FAILED, "결제 처리 중 오류가 발생했습니다.");
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
