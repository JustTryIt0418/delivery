package com.example.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
// 하위 패키지가 아니면 자동으로 스캔하지 못하기 때문에 특정 패키지를 스캔하도록 설정
@EntityScan(basePackages = "com.example.db")
@EnableJpaRepositories(basePackages = "com.example.db")
public class JpaConfig {

}
