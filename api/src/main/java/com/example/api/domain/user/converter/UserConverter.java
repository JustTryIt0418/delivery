package com.example.api.domain.user.converter;

import com.example.api.common.annotation.Converter;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.user.controller.model.UserRegisterRequest;
import com.example.api.domain.user.controller.model.UserResponse;
import com.example.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(it ->
                        UserEntity.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                                .address(request.getAddress())
                                .password(request.getPassword())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest is null"));
    }

    public UserResponse toResponse(UserEntity entity) {
        return Optional.ofNullable(entity)
                .map(it ->
                        UserResponse.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .status(entity.getStatus())
                                .email(entity.getEmail())
                                .address(entity.getAddress())
                                .registeredAt(entity.getRegisteredAt())
                                .unregisteredAt(entity.getUnregisteredAt())
                                .lastLoginAt(entity.getLastLoginAt())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity is null"));
    }

}
