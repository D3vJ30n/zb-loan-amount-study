package com.example.demo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String accountNumber;
}
