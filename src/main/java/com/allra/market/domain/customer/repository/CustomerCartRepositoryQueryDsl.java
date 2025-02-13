package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.model.dto.response.GetCustomerCartProductResponse;

import java.util.List;

public interface CustomerCartRepositoryQueryDsl {
    List<GetCustomerCartProductResponse> search();
}
