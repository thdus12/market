package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderProductResponse;

import java.util.List;

public interface CustomerOrderRepositoryQueryDsl {
    public List<GetCustomerOrderProductResponse> search(Long customerOrderId);
}
