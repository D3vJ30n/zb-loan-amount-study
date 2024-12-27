package com.example.api.dto.response

import java.time.LocalDateTime

data class LoanApplicationResponse(
    val loanLimit: Long,          // 대출 한도 금액
    val interestRate: Double,     // 금리
    val loanTerm: Int,           // 대출 기간 (개월)
    val applicationId: String,    // 신청 ID
    val createdAt: LocalDateTime  // 신청 일시
)