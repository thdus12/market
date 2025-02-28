package com.api.market.domain.customer.repository;

import com.api.market.domain.customer.model.dto.response.GetCustomerCartProductResponse;

import java.util.List;

public interface CustomerCartRepositoryQueryDsl {
    List<GetCustomerCartProductResponse> search();
}
