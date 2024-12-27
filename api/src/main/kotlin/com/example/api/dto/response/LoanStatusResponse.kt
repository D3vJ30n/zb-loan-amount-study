package com.example.api.dto.response

import com.example.api.dto.type.LoanStatus
import java.time.LocalDateTime

data class LoanStatusResponse(
    val applicationId: String,     // 신청 ID
    val status: LoanStatus,       // 신청 상태
    val updatedAt: LocalDateTime, // 상태 변경 일시
    val message: String?          // 상태 메시지
)