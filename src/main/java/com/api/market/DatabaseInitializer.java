package com.api.market;

import com.api.market.domain.customer.entity.Customer;
import com.api.market.domain.customer.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final CustomerRepository customerRepository;

        public void dbInit() {
            // 사용자 임시 데이터 추가
            if (customerRepository.count() == 0) {
                customerRepository.save(new Customer("bthdus5890@gmail.com", "배소연"));
            }
        }
    }
}