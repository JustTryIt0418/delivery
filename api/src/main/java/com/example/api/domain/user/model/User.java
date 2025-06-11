package com.example.api.domain.user.model;

import com.example.db.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserStatus status;

    private String address;

    private OffsetDateTime registeredAt;

    private OffsetDateTime unregisteredAt;

    private OffsetDateTime lastLoginAt;
}
