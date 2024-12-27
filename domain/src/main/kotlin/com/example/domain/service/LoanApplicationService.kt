package com.example.domain.service

import com.example.domain.entity.LoanApplication
import com.example.domain.repository.LoanApplicationRepository
import org.springframework.stereotype.Service

@Service
class LoanApplicationService(private val loanApplicationRepository: LoanApplicationRepository) {

    fun createLoan(loanApplication: LoanApplication): LoanApplication =
        loanApplicationRepository.save(loanApplication)

    fun getLoanById(id: Long): LoanApplication =
        loanApplicationRepository.findById(id).orElseThrow { RuntimeException("Loan not found") }
}
