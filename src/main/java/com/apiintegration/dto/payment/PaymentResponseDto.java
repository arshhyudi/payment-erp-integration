package com.apiintegration.dto.payment;


import com.apiintegration.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private String orderId;
    private PaymentStatus status;
    private String message;
}
