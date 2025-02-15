package com.allra.market.service;

import com.allra.market.domain.common.provider.CustomerProvider;
import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.entity.CustomerOrder;
import com.allra.market.domain.customer.model.dto.response.GetCustomerOrderProductResponse;
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

    public List<GetCustomerOrderResponse> list() {
        Customer customer = customerProvider.getCustomer();
        List<CustomerOrder> customerOrders = customerOrderRepository.findByCustomerOrderByCreatedDateDesc(customer);

        return customerOrders.stream()
                .map(order -> {
                    List<GetCustomerOrderProductResponse> products = customerOrderRepository.search(order.getId());
                    return new GetCustomerOrderResponse(order.getId(), order.getTotalAmount(), order.getStatus(), products);
                })
                .toList();
    }

    public void save(CustomerOrder customerOrder) {
        customerOrderRepository.save(customerOrder);
    }
}
