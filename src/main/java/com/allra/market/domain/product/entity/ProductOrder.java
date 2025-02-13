package com.allra.market.domain.product.entity;

import com.allra.market.domain.common.entity.BaseTimeEntity;
import com.allra.market.domain.customer.entity.CustomerOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Comment("상품 주문")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Comment("order.id")
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Comment("product.id")
    private Product product;

    @Column(nullable = false)
    @Comment("주문 수량")
    private Integer quantity;

    @Column(nullable = false)
    @Comment("주문 당시 가격")
    private Long price;

    @Builder
    public ProductOrder(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public Long getTotalPrice() {
        return price * quantity;
    }

    protected void setOrder(CustomerOrder order) {
        this.order = order;
    }
}