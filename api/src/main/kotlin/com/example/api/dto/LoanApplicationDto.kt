package com.example.api.dto

import com.example.domain.entity.LoanApplication

data class LoanApplicationDto(
    val applicantName: String,
    val amount: Double
) {
    fun toEntity() = LoanApplication(applicantName = applicantName, amount = amount)

    companion object {
        fun fromEntity(entity: LoanApplication) =
            LoanApplicationDto(applicantName = entity.applicantName, amount = entity.amount)
    }
}
