package com.example.api.domain.user.controller;

import com.example.api.common.annotation.UserSession;
import com.example.api.common.api.Api;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.user.business.UserBusiness;
import com.example.api.domain.user.controller.model.UserResponse;
import com.example.api.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
            @UserSession User user
    ) {
        return Api.ok(userBusiness.me(user.getId()));
    }
}
