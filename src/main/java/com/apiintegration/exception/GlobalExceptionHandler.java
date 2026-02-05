package com.apiintegration.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicatePayment(
            ResourceAlreadyExistsException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error"
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
//   @ExceptionHandler(ResourceAlreadyExistsException.class)
//    public ResponseEntity<ApiErrorResponse> handleResourceAlreadyExistsException(
//            ResourceAlreadyExistsException ex) {
//
//        ApiErrorResponse response = ApiErrorResponse.builder()
//                .success(false)
//                .message(ex.getMessage())
//                .build();
//
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT) // 409
//                .body(response);
//    }


}
