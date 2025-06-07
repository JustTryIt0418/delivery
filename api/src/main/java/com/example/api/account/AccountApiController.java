package com.example.api.account;

import com.example.api.account.model.AccountMeResponse;
import com.example.db.account.AccountEntity;
import com.example.db.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public AccountMeResponse me() {
        return AccountMeResponse.builder()
                .name("홍길동")
                .email("hong@gmail.com")
                .registeredAt(LocalDateTime.now())
                .build();
    }
}
