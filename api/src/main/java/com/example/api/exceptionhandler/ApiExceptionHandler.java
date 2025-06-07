package com.example.api.exceptionhandler;

import com.example.api.common.api.Api;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.error.ErrorCodeIfs;
import com.example.api.common.exception.ApiException;
import com.example.api.common.exception.ApiExceptionIfs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException) {
        log.error("", apiException);

        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.error(errorCode, apiException.getErrorDescription())
                );
    }
}
