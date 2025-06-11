package com.example.api.domain.user.business;

import com.example.api.common.annotation.Business;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.error.UserErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.token.business.TokenBusiness;
import com.example.api.domain.token.controller.model.TokenResponse;
import com.example.api.domain.user.controller.model.UserLoginRequest;
import com.example.api.domain.user.controller.model.UserRegisterRequest;
import com.example.api.domain.user.controller.model.UserResponse;
import com.example.api.domain.user.converter.UserConverter;
import com.example.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    public UserResponse register(UserRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest is null"));
    }

    public TokenResponse login(UserLoginRequest request) {
        return Optional.ofNullable(request)
                .map(it ->
                        userService.getUserWithThrow(it.getEmail(), it.getPassword())
                )
                .map(tokenBusiness::issueToken)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public UserResponse me(Long userId) {
        return Optional.ofNullable(userId)
                .map(userService::getUserWithThrow)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));


    }
}
