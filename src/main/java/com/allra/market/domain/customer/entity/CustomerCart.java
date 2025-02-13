package com.allra.market.domain.customer.entity;

import com.allra.market.domain.common.entity.BaseTimeEntity;
import com.allra.market.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Comment("사용자 장바구니")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerCart extends BaseTimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -8787152271426160012L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Comment("customer.id")
    private Customer customer;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Comment("product.id")
    private Product product;

    // 수량
    @Column(nullable = false)
    @ColumnDefault("1")
    @Comment("수량")
    private Integer quantity;  // 수량 필드 추가 필요

    public CustomerCart(Customer customer, Product product, Integer quantity) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 주문 금액 계산
    public long getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

