package com.apiintegration.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    @NotBlank
    private String orderId;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currency;
}
