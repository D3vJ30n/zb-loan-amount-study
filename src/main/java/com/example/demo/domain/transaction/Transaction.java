package com.example.demo.domain.transaction;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 거래 정보 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Transaction {
    /**
     * 거래 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 거래가 발생한 계좌번호
     */
    @Column(nullable = false)
    private String accountNumber;

    /**
     * 거래 유형
     */
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    /**
     * 거래 결과 상태
     */
    @Enumerated(EnumType.STRING)
    private TransactionResultType transactionResultType;

    /**
     * 거래 금액
     */
    @Column(nullable = false)
    private Long amount;

    /**
     * 거래 후 계좌 잔액
     */
    private Long balanceSnapshot;

    /**
     * 거래 고유 번호
     */
    private String transactionId;

    /**
     * 거래 일시
     */
    private LocalDateTime transactedAt;
}
