package com.api.market.domain.product.repository;

import com.api.market.domain.product.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueryDsl {
    Optional<Product> findByIdAndEnabledIsTrue(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id AND p.enabled = true")
    Optional<Product> findByIdWithLock(@Param("id") Long id);
}