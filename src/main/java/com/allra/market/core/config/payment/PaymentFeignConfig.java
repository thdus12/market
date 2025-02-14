package com.allra.market.core.config.payment;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentFeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;  // 전체 로깅
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder.Default();  // 기본 에러 디코더 사용
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            // 필요한 헤더 추가
        };
    }
}