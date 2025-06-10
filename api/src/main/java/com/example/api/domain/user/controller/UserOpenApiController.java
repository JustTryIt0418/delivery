package com.example.api.domain.user.controller;

import com.example.api.common.api.Api;
import com.example.api.domain.token.controller.model.TokenResponse;
import com.example.api.domain.user.business.UserBusiness;
import com.example.api.domain.user.controller.model.UserLoginRequest;
import com.example.api.domain.user.controller.model.UserRegisterRequest;
import com.example.api.domain.user.controller.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ) {
        return Api.ok(
                userBusiness.register(request.getBody())
        );
    }

    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody Api<UserLoginRequest> request
    ) {
        return Api.ok(
                userBusiness.login(request.getBody())
        );
    }
}
