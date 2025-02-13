package com.allra.market.domain.customer.entity;

import com.allra.market.domain.common.entity.BaseTimeEntity;
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

@Entity
@Getter
@Comment("사용자")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseTimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -8787152271426160012L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 320, unique = true)
    @Comment("이메일")
    protected String email;

    @Column(nullable = false)
    @Comment("이름")
    private String name;

    public Customer(String email, String name) {
        this.email = email;
        this.name = name;
    }
}

