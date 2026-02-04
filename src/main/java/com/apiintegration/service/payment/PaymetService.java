package com.apiintegration.service.payment;

import com.apiintegration.dto.payment.PaymentRequest;


public interface PaymetService {
    String createPayment(PaymentRequest request);
}
