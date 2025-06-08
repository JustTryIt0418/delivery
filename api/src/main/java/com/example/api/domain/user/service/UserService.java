package com.example.api.domain.user.service;

import com.example.api.common.error.ErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.db.user.UserEntity;
import com.example.db.user.UserRepository;
import com.example.db.user.enums.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity register(UserEntity user) {
        return Optional.ofNullable(user)
                .map(it -> {
                    it.setStatus(UserStatus.REGISTERED);
                    it.setRegisteredAt(OffsetDateTime.now());
                    return userRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity is null"));
    }

}
