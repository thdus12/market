package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.model.dto.response.GetCustomerCartResponse;

import java.util.List;

public interface CustomerCartRepositoryQueryDsl {
    List<GetCustomerCartResponse> search();
}
