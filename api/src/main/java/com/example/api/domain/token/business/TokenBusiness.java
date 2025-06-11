package com.example.api.domain.token.business;

import com.example.api.common.annotation.Business;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.token.controller.model.TokenResponse;
import com.example.api.domain.token.converter.TokenConverter;
import com.example.api.domain.token.model.TokenDto;
import com.example.api.domain.token.service.TokenService;
import com.example.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenConverter tokenConverter;
    private final TokenService tokenService;

    public TokenResponse issueToken(
            UserEntity userEntity
    ) {
        return Optional.ofNullable(userEntity)
                .map(entity ->
                    userEntity.getId()
                )
                .map(userId -> {
                    TokenDto accessToken = tokenService.issueAccessToken(userId);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public Long validationAccessToken (String accessToken) {
        return Optional.ofNullable(accessToken)
                .map(tokenService::validationToken)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Access token is null"));
    }

    public Long validationRefreshToken (String accessToken) {
        return Optional.ofNullable(accessToken)
                .map(tokenService::validationToken)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Access token is null"));
    }
}
