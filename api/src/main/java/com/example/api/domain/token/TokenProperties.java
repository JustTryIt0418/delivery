package com.example.api.domain.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    private String secretKey;
    private Long accessTokenPlusHour;
    private Long refreshTokenPlusHour;
}
