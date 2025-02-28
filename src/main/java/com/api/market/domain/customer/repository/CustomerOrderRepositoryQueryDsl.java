package com.api.market.domain.customer.repository;

import com.api.market.domain.customer.model.dto.request.GetCustomerOrderRequest;
import com.api.market.domain.customer.model.dto.response.GetCustomerOrderResponse;

import java.util.List;

public interface CustomerOrderRepositoryQueryDsl {
    List<GetCustomerOrderResponse> search(Long customerId, GetCustomerOrderRequest request);
}
