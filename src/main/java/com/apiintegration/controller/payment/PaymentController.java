package com.apiintegration.controller.payment;


import com.apiintegration.dto.payment.PaymentRequestDto;
import com.apiintegration.dto.payment.PaymentResponseDto;
import com.apiintegration.service.payment.PaymentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentServiceImpl  paymentService;
    @PostMapping("/new/payment")
    public ResponseEntity<PaymentResponseDto> createPayment(
            @Valid @RequestBody PaymentRequestDto requestDto) {

        return ResponseEntity.ok(paymentService.createPayment(requestDto));
    }
}
