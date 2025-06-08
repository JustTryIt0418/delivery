package com.example.api.domain.user.business;

import com.example.api.common.annotation.Business;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.user.controller.model.UserRegisterRequest;
import com.example.api.domain.user.controller.model.UserResponse;
import com.example.api.domain.user.converter.UserConverter;
import com.example.api.domain.user.service.UserService;
import com.example.db.user.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserResponse register(UserRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest is null"));
    }
}
