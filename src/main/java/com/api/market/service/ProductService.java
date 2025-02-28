package com.api.market.service;

import com.api.market.core.config.error.entity.ApiException;
import com.api.market.core.config.error.entity.ErrorCode;
import com.api.market.domain.product.entity.Product;
import com.api.market.domain.product.model.dto.request.GetProductRequest;
import com.api.market.domain.product.model.dto.request.PostProductRequest;
import com.api.market.domain.product.model.dto.request.PutProductRequest;
import com.api.market.domain.product.model.dto.response.GetProductDetailResponse;
import com.api.market.domain.product.model.dto.response.GetProductResponse;
import com.api.market.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductRepository productRepository;

    public Page<GetProductResponse> list(GetProductRequest dto, Pageable pageable) {
        return productRepository.search(dto, pageable);
    }

    public GetProductDetailResponse detail(Long id) {
        Product product = getProduct(id);

        if (product.getQuantity() == 0) {
            throw new ApiException(ErrorCode.SOLD_OUT, "품절된 상품 입니다.");
        }

        return new GetProductDetailResponse(product);
    }

    public Boolean insert(PostProductRequest dto) {
        Product product = new Product(dto);
        productRepository.save(product);

        return true;
    }

    public Boolean update(Long id, PutProductRequest dto) {
        Product product = getProduct(id);
        product.update(dto);
        return true;
    }

    public Boolean delete(Long id) {
        Product product = getProduct(id);
        product.disabled();
        return true;
    }

    public Product getProduct(Long id) {
        return productRepository.findByIdAndEnabledIsTrue(id)
            .orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND, "존재하지 않는 상품 입니다."));
    }

    public Product findProductWithLock(Long id) {
        return productRepository.findByIdWithLock(id)
            .orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND, "존재하지 않는 상품 입니다."));
    }
}
