package com.apiintegration.service.payment;

import com.apiintegration.dto.payment.PaymentRequestDto;
import com.apiintegration.dto.payment.PaymentResponseDto;
import com.apiintegration.entity.payment.Payment;
import com.apiintegration.enums.PaymentStatus;
import com.apiintegration.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymetService{
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        paymentRepository.findByOrderId(requestDto.getOrderId())
                .ifPresent(payment -> {
                    throw new IllegalArgumentException(
                            "Payment already exists for orderId: " + requestDto.getOrderId()
                    );
                });
        Payment payment = Payment.builder()
                .orderId(requestDto.getOrderId())
                .amount(requestDto.getAmount())
                .currency(requestDto.getCurrency())
                .status(PaymentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return new PaymentResponseDto(
                payment.getOrderId(),
                payment.getStatus(),
                "Payment created successfully"
        );
    }
}
