package com.example.demo.controller;

import com.example.demo.domain.dto.request.CreateAccountRequest;
import com.example.demo.domain.dto.request.DeleteAccountRequest;
import com.example.demo.domain.dto.response.AccountResponse;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
        @RequestBody @Valid CreateAccountRequest request) {
        return ResponseEntity.ok(
            accountService.createAccount(request)
        );
    }

    @DeleteMapping
    public ResponseEntity<AccountResponse> deleteAccount(
        @RequestBody @Valid DeleteAccountRequest request) {
        return ResponseEntity.ok(
            accountService.deleteAccount(request)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(
        @PathVariable("userId") Long userId) { // "userId" 명시
        return ResponseEntity.ok(
            accountService.getAccountsByUserId(userId)
        );
    }
}
