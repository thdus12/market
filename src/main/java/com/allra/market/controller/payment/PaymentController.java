package com.allra.market.controller.payment;

import com.allra.market.domain.customer.model.dto.request.PostCustomerOrderCartPaymentRequest;
import com.allra.market.domain.customer.model.dto.request.PostCustomerOrderPaymentRequest;
import com.allra.market.domain.customer.model.dto.response.PostCustomerOrderResponse;
import com.allra.market.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    // 바로 구매
    @PostMapping("/purchase")
    public ResponseEntity<PostCustomerOrderResponse> purchase(@Valid @RequestBody PostCustomerOrderPaymentRequest dto) {
        return ResponseEntity.ok(paymentService.purchase(dto));
    }

    // 장바구니 구매
    @PostMapping("/carts/purchase")
    public ResponseEntity<PostCustomerOrderResponse> cartPurchase(@Valid @RequestBody PostCustomerOrderCartPaymentRequest dto) {
        return ResponseEntity.ok(paymentService.cartPurchase(dto));
    }
}
