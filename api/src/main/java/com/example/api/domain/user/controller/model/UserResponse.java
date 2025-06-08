package com.example.api.domain.user.controller.model;

import com.example.db.user.UserEntity;
import com.example.db.user.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private UserStatus status;

    private String address;

    private OffsetDateTime registeredAt;

    private OffsetDateTime unregisteredAt;

    private OffsetDateTime lastLoginAt;
}
