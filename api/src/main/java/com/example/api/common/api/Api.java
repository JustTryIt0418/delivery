package com.example.api.common.api;

import com.example.api.common.error.ErrorCodeIfs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> ok(T data) {
        return Api.<T>builder()
                .result(Result.ok())
                .body(data)
                .build();
    }

    public static Api<Object> error(Result result) {
        return Api.builder()
                .result(result)
                .build();
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs) {
        return Api.builder()
                .result(Result.error(errorCodeIfs))
                .build();
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs, String description) {
        return Api.builder()
                .result(Result.error(errorCodeIfs, description))
                .build();
    }
}
