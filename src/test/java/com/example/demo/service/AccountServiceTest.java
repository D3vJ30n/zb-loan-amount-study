package com.example.demo.service;

import com.example.demo.domain.account.Account;
import com.example.demo.domain.account.AccountStatus;
import com.example.demo.domain.dto.request.CreateAccountRequest;
import com.example.demo.domain.dto.request.DeleteAccountRequest;
import com.example.demo.domain.exception.AccountException;
import com.example.demo.domain.exception.ErrorCode;
import com.example.demo.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setup() {
        lenient().when(accountRepository.findByUserId(any()))
            .thenReturn(Arrays.asList());
    }

    @Test
    @DisplayName("계좌 생성 성공")
    void createAccountSuccess() {
        // given
        given(accountRepository.save(any()))
            .willReturn(Account.builder()
                .accountNumber("1000000000")
                .userId(1L)
                .balance(1000L)
                .build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        accountService.createAccount(new CreateAccountRequest(1L, 1000L));

        // then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(1000L, captor.getValue().getBalance());
        assertEquals(1L, captor.getValue().getUserId());
    }

    @Test
    @DisplayName("계좌 생성 실패 - 최대 계좌 수 초과")
    void createAccount_maxAccountsExceeded() {
        // given
        given(accountRepository.findByUserId(any()))
            .willReturn(Arrays.asList(
                Account.builder().build(), Account.builder().build(), Account.builder().build(),
                Account.builder().build(), Account.builder().build(), Account.builder().build(),
                Account.builder().build(), Account.builder().build(), Account.builder().build(),
                Account.builder().build()
            ));

        // when & then
        AccountException exception = assertThrows(AccountException.class,
            () -> accountService.createAccount(new CreateAccountRequest(1L, 1000L)));
        assertEquals(ErrorCode.MAX_ACCOUNT_PER_USER_10, exception.getErrorCode());
    }

    @Test
    @DisplayName("계좌 해지 성공")
    void deleteAccountSuccess() {
        // given
        given(accountRepository.findByAccountNumber(anyString()))
            .willReturn(Optional.of(Account.builder()
                .accountNumber("1000000000")
                .userId(1L)
                .balance(0L)
                .accountStatus(AccountStatus.IN_USE)
                .build()));

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        accountService.deleteAccount(new DeleteAccountRequest(1L, "1000000000"));

        // then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(AccountStatus.UNREGISTERED, captor.getValue().getAccountStatus());
    }

    @Test
    @DisplayName("계좌 해지 실패 - 잔액 있음")
    void deleteAccount_hasBalance() {
        // given
        given(accountRepository.findByAccountNumber(anyString()))
            .willReturn(Optional.of(Account.builder()
                .accountNumber("1000000000")
                .userId(1L)
                .balance(100L)
                .accountStatus(AccountStatus.IN_USE)
                .build()));

        // when & then
        AccountException exception = assertThrows(AccountException.class,
            () -> accountService.deleteAccount(new DeleteAccountRequest(1L, "1000000000")));
        assertEquals(ErrorCode.BALANCE_NOT_EMPTY, exception.getErrorCode());
    }
}

