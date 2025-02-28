package com.api.market.domain.customer.repository;

import com.api.market.domain.customer.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, CustomerOrderRepositoryQueryDsl {

}