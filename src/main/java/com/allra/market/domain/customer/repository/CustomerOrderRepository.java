package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, CustomerOrderRepositoryQueryDsl {
    List<CustomerOrder> findByCustomerOrderByCreatedDateDesc(Customer customer);
}