package com.apiintegration.exception;

import lombok.Builder;
import lombok.Setter;
import org.springframework.amqp.rabbit.listener.MicrometerHolder;

import java.time.LocalDateTime;

@Setter
public class ApiErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
//
//    public static MicrometerHolder builder() {
//    }
}
