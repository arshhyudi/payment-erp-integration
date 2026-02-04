package com.apiintegration.service.payment;

import com.apiintegration.dto.payment.PaymentRequest;
import com.apiintegration.entity.payment.Payment;
import com.apiintegration.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymetService{
    private final PaymentRepository paymentRepository;

    @Override
    public String createPayment(PaymentRequest request) {

        Payment payment = Payment.builder()
                .transactionId(UUID.randomUUID().toString())
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("RECEIVED")
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
        return payment.getTransactionId();
    }
}
