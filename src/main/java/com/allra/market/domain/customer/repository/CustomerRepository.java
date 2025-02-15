package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}