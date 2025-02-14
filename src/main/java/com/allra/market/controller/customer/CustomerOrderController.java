package com.allra.market.controller.customer;

import com.allra.market.domain.customer.model.dto.request.PostCustomerOrderCartPaymentRequest;
import com.allra.market.domain.customer.model.dto.request.PostCustomerOrderPaymentRequest;
import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderResponse;
import com.allra.market.service.CustomerOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/orders")
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    // 주문 목록
    @GetMapping
    public ResponseEntity<List<GetCustomerOrderResponse>> list() {
        return ResponseEntity.ok(customerOrderService.list());
    }

    // 바로 구매
    @PostMapping("/direct")
    public ResponseEntity<Boolean> direct(@Valid @RequestBody PostCustomerOrderPaymentRequest dto) {
        return ResponseEntity.ok(customerOrderService.direct(dto));
    }

    // 장바구니 구매
    @PostMapping("/cart")
    public ResponseEntity<Boolean> cart(@Valid @RequestBody PostCustomerOrderCartPaymentRequest dto) {
        return ResponseEntity.ok(customerOrderService.cart(dto));
    }
}
