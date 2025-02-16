package com.allra.market.service;

import com.allra.market.domain.common.provider.CustomerProvider;
import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.entity.CustomerOrder;
import com.allra.market.domain.customer.model.dto.request.GetCustomerOrderRequest;
import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderResponse;
import com.allra.market.domain.customer.repository.CustomerOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerOrderService {
    private final CustomerProvider customerProvider;
    private final CustomerOrderRepository customerOrderRepository;

    public List<GetCustomerOrderResponse> list(GetCustomerOrderRequest dto) {
        Customer customer = customerProvider.getCustomer();
        return customerOrderRepository.search(customer.getId(), dto);
    }

    public void save(CustomerOrder customerOrder) {
        customerOrderRepository.save(customerOrder);
    }
}
