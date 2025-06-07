package com.example.api.exceptionhandler;

import com.example.api.common.api.Api;
import com.example.api.common.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(Exception exception) {
        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(
                        Api.error(ErrorCode.SERVER_ERROR)
                );
    }
}
