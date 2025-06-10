package com.example.api.domain.token.helper;

import com.example.api.common.error.ErrorCode;
import com.example.api.common.error.TokenErrorCode;
import com.example.api.common.exception.ApiException;
import com.example.api.domain.token.TokenProperties;
import com.example.api.domain.token.ifs.TokenHelperIfs;
import com.example.api.domain.token.model.TokenDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.ParserBuilder;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper implements TokenHelperIfs {

    private final TokenProperties tokenProperties;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(tokenProperties.getAccessTokenPlusHour());

        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        SecretKey key = Keys.hmacShaKeyFor(tokenProperties.getSecretKey().getBytes());

        String token = Jwts.builder()
                .signWith(key)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(token)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(tokenProperties.getRefreshTokenPlusHour());

        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        SecretKey key = Keys.hmacShaKeyFor(tokenProperties.getSecretKey().getBytes());

        String token = Jwts.builder()
                .signWith(key)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(token)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        SecretKey key = Keys.hmacShaKeyFor(tokenProperties.getSecretKey().getBytes());

        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .json(new JacksonDeserializer<>())
                .build();

        try {
            var result = parser.parseSignedClaims(token);
            return new HashMap<>(result.getPayload());
        } catch (Exception e) {
            if (e instanceof SignatureException signatureException) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, signatureException);
            } else if (e instanceof ExpiredJwtException expiredJwtException) {
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, expiredJwtException);
            } else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}
