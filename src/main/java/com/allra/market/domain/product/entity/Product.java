package com.allra.market.domain.product.entity;

import com.allra.market.domain.common.entity.BaseTimeEntity;
import com.allra.market.domain.product.model.dto.request.PostProductRequest;
import com.allra.market.domain.product.model.dto.request.PutProductRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Comment("상품")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("상품명")
    private String name;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Comment("설명")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment("결제 금액")
    private Long price;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment("수량")
    private Integer quantity;

    @Column(nullable = false)
    @ColumnDefault("1")
    @Comment("활성화 여부(삭제 여부)")
    private Boolean enabled;

    public Product(PostProductRequest dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.quantity = dto.getQuantity();
        this.enabled = true;
    }

    public void update(PutProductRequest dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.quantity = dto.getQuantity();
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void disabled() {
        this.enabled = false;
    }

    public void decreaseStock(int quantity) {
        this.quantity -= quantity;
    }
}

