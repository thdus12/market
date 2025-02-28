package com.api.market.domain.common.provider;

import com.api.market.core.config.error.entity.ApiException;
import com.api.market.core.config.error.entity.ErrorCode;
import com.api.market.domain.customer.entity.Customer;
import com.api.market.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProvider {
    private final CustomerRepository customerRepository;

    // 임시 사용자 계정
    public Customer getCustomer() {
        return customerRepository.findById(1L)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, "존재하지 않는 사용자 입니다."));
    }
}