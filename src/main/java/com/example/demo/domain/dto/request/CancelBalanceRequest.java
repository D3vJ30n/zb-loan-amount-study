package com.example.demo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CancelBalanceRequest {
    @NotBlank
    private String transactionId;

    @NotBlank
    private String accountNumber;

    @NotNull
    @Min(10)
    private Long amount;
}
