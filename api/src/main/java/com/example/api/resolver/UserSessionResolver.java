package com.example.api.resolver;

import com.example.api.common.annotation.UserSession;
import com.example.api.common.error.ErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.user.business.UserBusiness;
import com.example.api.domain.user.model.User;
import com.example.api.domain.user.service.UserService;
import com.example.db.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. UserSession 어노테이션이 붙어있는지 체크
        Boolean annotation = parameter.hasParameterAnnotation(UserSession.class);
        // 2. 파라미터 타입이 User 인지 체크
        Boolean parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // support parameter 에서 true 반환 시 호출

        // request context holder 에서 찾아오기
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(it -> it.getAttribute("userId", RequestAttributes.SCOPE_REQUEST))
                .map(Object::toString)
                .map(Long::parseLong)
                .map(userService::getUserWithThrow)
                .map(userEntity ->
                        User.builder()
                                .id(userEntity.getId())
                                .email(userEntity.getEmail())
                                .name(userEntity.getName())
                                .address(userEntity.getAddress())
                                .status(userEntity.getStatus())
                                .password(userEntity.getPassword())
                                .registeredAt(userEntity.getRegisteredAt())
                                .unregisteredAt(userEntity.getUnregisteredAt())
                                .lastLoginAt(userEntity.getLastLoginAt())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "userId not found in request attributes"));
    }
}
