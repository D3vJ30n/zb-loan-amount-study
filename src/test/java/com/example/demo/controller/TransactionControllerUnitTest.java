package com.example.demo.controller;

import com.example.demo.domain.dto.request.CancelBalanceRequest;
import com.example.demo.domain.dto.request.UseBalanceRequest;
import com.example.demo.domain.dto.response.TransactionResponse;
import com.example.demo.domain.transaction.TransactionType;
import com.example.demo.service.TransactionService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerUnitTest { // 클래스명 변경

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc; // MockMvc 선언
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build(); // 초기화
    }

    @Test
    @DisplayName("잔액 사용 성공")
    void useBalanceSuccess() throws Exception {
        // given
        given(transactionService.useBalance(any()))
            .willReturn(TransactionResponse.builder()
                .accountNumber("1000000000")
                .transactionType(TransactionType.USE)
                .amount(1000L)
                .transactedAt(LocalDateTime.now())
                .build());

        // when & then
        mockMvc.perform(post("/api/v1/transactions/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new UseBalanceRequest(1L, "1000000000", 1000L))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountNumber").value("1000000000"))
            .andExpect(jsonPath("$.transactionType").value("USE"))
            .andExpect(jsonPath("$.amount").value(1000));
    }

    @Test
    @DisplayName("잔액 사용 취소 성공")
    void cancelBalanceSuccess() throws Exception {
        // given
        given(transactionService.cancelBalance(any()))
            .willReturn(TransactionResponse.builder()
                .accountNumber("1000000000")
                .transactionType(TransactionType.CANCEL)
                .amount(1000L)
                .transactedAt(LocalDateTime.now())
                .build());

        // when & then
        mockMvc.perform(post("/api/v1/transactions/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new CancelBalanceRequest("transactionId", "1000000000", 1000L))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountNumber").value("1000000000"))
            .andExpect(jsonPath("$.transactionType").value("CANCEL"))
            .andExpect(jsonPath("$.amount").value(1000));
    }

    @Test
    @DisplayName("거래 조회 성공")
    void queryTransactionSuccess() throws Exception {
        // given
        given(transactionService.queryTransaction(any()))
            .willReturn(TransactionResponse.builder()
                .accountNumber("1000000000")
                .transactionType(TransactionType.USE)
                .amount(1000L)
                .transactedAt(LocalDateTime.now())
                .build());

        // when & then
        mockMvc.perform(get("/api/v1/transactions/{transactionId}", "transactionIdValue"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountNumber").value("1000000000"))
            .andExpect(jsonPath("$.transactionType").value("USE"))
            .andExpect(jsonPath("$.amount").value(1000));
    }
}