package com.allra.market.domain.customer.entity;

import com.allra.market.domain.common.entity.BaseTimeEntity;
import com.allra.market.domain.customer.type.OrderStatus;
import com.allra.market.domain.product.entity.ProductOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Comment("사용자 주문")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerOrder extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Comment("customer.id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> orderItems = new ArrayList<>();

    @Column(nullable = false)
    @Comment("주문 상태")
    @Convert(converter = OrderStatus.Converter.class)
    private OrderStatus status;

    @Column(nullable = false)
    @Comment("총 주문 금액")
    private Long totalAmount;

    // 주문 상태 변경
    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}

