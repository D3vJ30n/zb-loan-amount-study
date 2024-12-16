package com.example.demo.controller;

import com.example.demo.domain.dto.request.CancelBalanceRequest;
import com.example.demo.domain.dto.request.UseBalanceRequest;
import com.example.demo.domain.dto.response.TransactionResponse;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/use")
    public ResponseEntity<TransactionResponse> useBalance(
        @RequestBody @Valid UseBalanceRequest request) {
        return ResponseEntity.ok(
            transactionService.useBalance(request)
        );
    }

    @PostMapping("/cancel")
    public ResponseEntity<TransactionResponse> cancelBalance(
        @RequestBody @Valid CancelBalanceRequest request) {
        return ResponseEntity.ok(
            transactionService.cancelBalance(request)
        );
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> queryTransaction(
        @PathVariable("transactionId") String transactionId) { // 명시적으로 이름 지정
        return ResponseEntity.ok(
            transactionService.queryTransaction(transactionId)
        );
    }
}
