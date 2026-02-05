package com.apiintegration.exception;

public class PaymentNotFoundException extends ResourceAlreadyExistsException{
    public PaymentNotFoundException(String orderId) {
        super("Payment not found for orderId: " + orderId);
    }
}
