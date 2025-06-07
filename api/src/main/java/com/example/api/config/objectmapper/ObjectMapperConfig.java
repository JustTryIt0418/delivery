package com.example.api.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModules(new Jdk8Module(), new JavaTimeModule())                        // JDK 8 버전 이후 클래스, LocalDateTime, LocalDate 등 Java 8 시간 API 지원
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)      // 알 수 없는 속성 무시
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)                        // 날짜 관련 직렬화
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);                // 스네이크 케이스
    }
}
