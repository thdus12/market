package com.api.market.controller.customer;

import com.api.market.domain.customer.model.dto.request.PatchCustomerCartRequest;
import com.api.market.domain.customer.model.dto.request.PostCustomerCartRequest;
import com.api.market.domain.customer.model.dto.response.GetCustomerCartResponse;
import com.api.market.service.CustomerCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/carts")
public class CustomerCartController {
    private final CustomerCartService customerCartService;
    
    @GetMapping
    public ResponseEntity<GetCustomerCartResponse> list() {
        return ResponseEntity.ok(customerCartService.list());
    }

    @PostMapping
    public ResponseEntity<Boolean> insert(@Valid @RequestBody PostCustomerCartRequest dto) {
        return ResponseEntity.ok(customerCartService.insert(dto));
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Boolean> updateQuantity(@PathVariable Long id, @Valid @RequestBody PatchCustomerCartRequest dto) {
        return ResponseEntity.ok(customerCartService.updateQuantity(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(customerCartService.delete(id));
    }

    @DeleteMapping("/clear")
    private ResponseEntity<Boolean> clear() {
        return ResponseEntity.ok(customerCartService.clear());
    }
}