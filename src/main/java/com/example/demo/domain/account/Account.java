package com.example.demo.domain.account;

import com.example.demo.domain.exception.AccountException;
import com.example.demo.domain.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String accountNumber;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    public void useBalance(Long amount) {
        if (amount > this.balance) {
            throw new AccountException(ErrorCode.BALANCE_NOT_EMPTY);
        }
        this.balance -= amount;
    }

    public void cancelBalance(Long amount) {
        balance += amount;
    }

    public void unregister() {
        if (balance > 0) {
            throw new IllegalArgumentException("잔액이 있는 계좌는 해지할 수 없습니다.");
        }
        accountStatus = AccountStatus.UNREGISTERED;
        unregisteredAt = LocalDateTime.now();
    }
}