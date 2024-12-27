package com.example.api.service

import com.example.api.dto.request.LoanApplicationRequest
import com.example.api.dto.response.LoanApplicationResponse
import com.example.api.dto.response.LoanStatusResponse

interface LoanService {
    fun checkLoanAmount(request: LoanApplicationRequest): LoanApplicationResponse
    fun getLoanStatus(applicationId: String): LoanStatusResponse
}