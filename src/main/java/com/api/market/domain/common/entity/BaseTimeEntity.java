package com.api.market.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("생성 일시")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    @Comment("수정 일시")
    private LocalDateTime lastModifiedDate;
}