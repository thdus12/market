package com.allra.market.domain.payment.client;

import com.allra.market.core.config.payment.PaymentFeignConfig;
import com.allra.market.domain.payment.model.dto.request.PostPaymentRefundRequest;
import com.allra.market.domain.payment.model.dto.request.PostPaymentRequest;
import com.allra.market.domain.payment.model.dto.response.PostPaymentResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "payment-api",
    url = "${payment.domain}",
    configuration = PaymentFeignConfig.class
)
public interface PaymentClient {

    @PostMapping("/api/payments")
    PostPaymentResponse payment(@Valid @RequestBody PostPaymentRequest dto);

    @PostMapping("/api/payment/refund")
    PostPaymentResponse refund(@Valid @RequestBody PostPaymentRefundRequest dto);
}