package com.example.demo.domain.dto.response;

import com.example.demo.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 계좌 정보 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    /**
     * 계좌번호
     */
    private String accountNumber;

    /**
     * 계좌 잔액
     */
    private Long balance;

    /**
     * 계좌 등록일시
     */
    private LocalDateTime registeredAt;

    /**
     * 계좌 해지일시
     */
    private LocalDateTime unregisteredAt;

    /**
     * Account 엔티티를 AccountResponse DTO로 변환
     * @param account 계좌 엔티티
     * @return AccountResponse 객체
     */
    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .registeredAt(account.getRegisteredAt())
            .unregisteredAt(account.getUnregisteredAt())
            .build();
    }
}
