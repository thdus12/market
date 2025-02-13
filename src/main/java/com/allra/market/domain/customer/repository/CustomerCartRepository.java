package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.entity.CustomerCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long>, CustomerCartRepositoryQueryDsl {

}