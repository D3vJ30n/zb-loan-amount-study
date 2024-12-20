package com.example.demo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 계좌 해지 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountRequest {
    /**
     * 계좌 해지를 요청하는 사용자 ID
     * 필수값
     */
    @NotNull
    private Long userId;

    /**
     * 해지할 계좌번호
     * 필수값이며 공백 불가
     */
    @NotBlank
    private String accountNumber;
}
