package com.example.demo.domain.dto.response;

import com.example.demo.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private String accountNumber;
    private Long balance;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt; // 해지 시간 필드 추가

    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .registeredAt(account.getRegisteredAt())
            .unregisteredAt(account.getUnregisteredAt()) // unregisteredAt 추가
            .build();
    }
}
