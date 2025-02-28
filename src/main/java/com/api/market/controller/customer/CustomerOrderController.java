package com.api.market.controller.customer;

import com.api.market.domain.customer.model.dto.request.GetCustomerOrderRequest;
import com.api.market.domain.customer.model.dto.response.GetCustomerOrderResponse;
import com.api.market.service.CustomerOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/orders")
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    // 주문 목록
    @GetMapping
    public ResponseEntity<List<GetCustomerOrderResponse>> list(@Valid GetCustomerOrderRequest dto) {
        return ResponseEntity.ok(customerOrderService.list(dto));
    }
}
