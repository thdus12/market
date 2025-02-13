package com.allra.market.domain.payment.entity;

import com.allra.market.domain.common.entity.BaseTimeEntity;
import com.allra.market.domain.customer.entity.CustomerOrder;
import com.allra.market.domain.customer.type.OrderStatus;
import com.allra.market.domain.payment.type.PaymentStatus;
import com.allra.market.domain.payment.type.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Comment("결제")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Comment("order.id")
    private CustomerOrder order;

    @Column(nullable = false)
    @Comment("결제 금액")
    private Long amount;

    @Column(nullable = false)
    @Comment("결제 상태")
    @Convert(converter = PaymentStatus.Converter.class)
    private PaymentStatus status;

    @Column(nullable = false)
    @Comment("결제 수단")
    @Convert(converter = PaymentType.Converter.class)
    private PaymentType type;

    @Column
    @Comment("거래 ID")
    private String transactionId;

    public Payment(CustomerOrder order, PaymentType type) {
        this.order = order;
        this.amount = order.getTotalAmount();
        this.type = type;
        this.status = PaymentStatus.PENDING;
    }

    public void approve(String pgTransactionId) {
        this.status = PaymentStatus.COMPLETED;
        this.transactionId = pgTransactionId;
        this.order.updateStatus(OrderStatus.PAID);
    }

    public void fail() {
        this.status = PaymentStatus.FAILED;
        this.order.updateStatus(OrderStatus.CANCELLED);
    }
}
