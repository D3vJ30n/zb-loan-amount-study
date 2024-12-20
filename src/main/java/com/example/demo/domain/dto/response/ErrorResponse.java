package com.example.demo.domain.dto.response;

import com.example.demo.domain.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 에러 응답 DTO
 * 모든 API 에러 응답에 공통적으로 사용됨
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    /**
     * 에러 코드
     */
    private ErrorCode errorCode;

    /**
     * 에러 메시지
     */
    private String errorMessage;
}
