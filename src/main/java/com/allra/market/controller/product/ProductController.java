package com.allra.market.controller.product;

import com.allra.market.domain.product.model.dto.request.GetProductRequest;
import com.allra.market.domain.product.model.dto.request.PostProductRequest;
import com.allra.market.domain.product.model.dto.request.PutProductRequest;
import com.allra.market.domain.product.model.dto.response.GetProductDetailResponse;
import com.allra.market.domain.product.model.dto.response.GetProductResponse;
import com.allra.market.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<GetProductResponse>> list(@Valid GetProductRequest dto, Pageable pageable) {
        return ResponseEntity.ok(productService.list(dto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductDetailResponse> detail(@PathVariable Long id) {
        return ResponseEntity.ok(productService.detail(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> insert(@Valid @RequestBody PostProductRequest dto) {
        return ResponseEntity.ok(productService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @Valid @RequestBody PutProductRequest dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }
}
