package com.example.demo.controller;

import com.example.demo.domain.dto.request.CreateAccountRequest;
import com.example.demo.domain.dto.request.DeleteAccountRequest;
import com.example.demo.domain.dto.response.AccountResponse;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // MockMvc 객체를 초기화합니다.
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    @DisplayName("계좌 생성 성공")
    void createAccountSuccess() throws Exception {
        // given
        given(accountService.createAccount(any()))
            .willReturn(AccountResponse.builder()
                .accountNumber("1000000000")
                .registeredAt(LocalDateTime.now())
                .build());

        // when & then
        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new CreateAccountRequest(1L, 1000L))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountNumber").value("1000000000"));
    }

    @Test
    @DisplayName("계좌 해지 성공")
    void deleteAccountSuccess() throws Exception {
        // given
        given(accountService.deleteAccount(any()))
            .willReturn(AccountResponse.builder()
                .accountNumber("1000000000")
                .unregisteredAt(LocalDateTime.now())
                .build());

        // when & then
        mockMvc.perform(delete("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new DeleteAccountRequest(1L, "1000000000"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountNumber").value("1000000000"));
    }

    @Test
    @DisplayName("계좌 조회 성공")
    void getAccountsSuccess() throws Exception {
        // given
        given(accountService.getAccountsByUserId(any()))
            .willReturn(Arrays.asList(
                AccountResponse.builder()
                    .accountNumber("1000000000")
                    .balance(1000L)
                    .build(),
                AccountResponse.builder()
                    .accountNumber("1000000001")
                    .balance(2000L)
                    .build()
            ));

        // when & then
        mockMvc.perform(get("/api/v1/accounts/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].accountNumber").value("1000000000"))
            .andExpect(jsonPath("$[0].balance").value(1000))
            .andExpect(jsonPath("$[1].accountNumber").value("1000000001"))
            .andExpect(jsonPath("$[1].balance").value(2000));
    }
}
