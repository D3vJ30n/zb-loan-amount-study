package com.example.demo.service;

import com.example.demo.domain.account.Account;
import com.example.demo.domain.account.AccountStatus;
import com.example.demo.domain.dto.request.CreateAccountRequest;
import com.example.demo.domain.dto.request.DeleteAccountRequest;
import com.example.demo.domain.dto.response.AccountResponse;
import com.example.demo.domain.exception.AccountException;
import com.example.demo.domain.exception.ErrorCode;
import com.example.demo.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RedisTestService redisTestService;

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        // 계좌 수 확인
        if (accountRepository.findByUserId(request.getUserId()).size() >= 10) {
            throw new AccountException(ErrorCode.MAX_ACCOUNT_PER_USER_10);
        }

        // 계좌번호 생성
        String newAccountNumber;
        do {
            newAccountNumber = String.format("%010d", new Random().nextInt(1000000000));
        } while (accountRepository.existsByAccountNumber(newAccountNumber));

        // 계좌 생성
        Account account = accountRepository.save(
            Account.builder()
                .accountNumber(newAccountNumber)
                .userId(request.getUserId())
                .balance(request.getInitialBalance())
                .accountStatus(AccountStatus.IN_USE)
                .registeredAt(LocalDateTime.now())
                .build()
        );

        return AccountResponse.from(account);
    }

    @Transactional
    public AccountResponse deleteAccount(DeleteAccountRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 사용자 일치 확인
        if (!account.getUserId().equals(request.getUserId())) {
            throw new AccountException(ErrorCode.USER_NOT_FOUND);
        }

        // 계좌 상태 확인
        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }

        // 잔액 확인
        if (account.getBalance() > 0) {
            throw new AccountException(ErrorCode.BALANCE_NOT_EMPTY);
        }

        account.unregister();
        return AccountResponse.from(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId).stream()
            .map(AccountResponse::from)
            .collect(Collectors.toList());
    }
}
