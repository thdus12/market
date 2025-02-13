package com.allra.market.domain.product.repository;

import com.allra.market.domain.product.model.dto.request.GetProductRequest;
import com.allra.market.domain.product.model.dto.response.GetProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryQueryDsl {
    Page<GetProductResponse> search(GetProductRequest dto, Pageable pageable);
}
