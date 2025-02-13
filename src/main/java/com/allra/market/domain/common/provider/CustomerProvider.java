package com.allra.market.domain.common.provider;

import com.allra.market.core.config.error.entity.ApiException;
import com.allra.market.core.config.error.entity.ErrorCode;
import com.allra.market.domain.customer.entity.Customer;
import com.allra.market.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProvider {
    private final CustomerRepository customerRepository;

    // 임시 사용자 계정
    public Customer getCustomer() {
        return customerRepository.findByEmail("user@test.com")
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, "존재하지 않는 사용자 입니다."));
    }
}