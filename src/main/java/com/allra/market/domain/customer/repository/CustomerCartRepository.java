package com.allra.market.domain.customer.repository;

import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.entity.CustomerCart;
import com.allra.market.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long>, CustomerCartRepositoryQueryDsl {
    Optional<CustomerCart> findByCustomerAndProduct(Customer customer, Product product);

    void deleteAllByCustomer(Customer customer);

    List<CustomerCart> findAllByCustomer(Customer customer);
}