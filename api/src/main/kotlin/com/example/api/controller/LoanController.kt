package com.example.api.controller

import com.example.api.dto.request.LoanApplicationRequest
import com.example.api.dto.response.LoanApplicationResponse
import com.example.api.dto.response.LoanStatusResponse
import com.example.api.service.LoanService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "대출", description = "대출 관련 API")
@RestController
@RequestMapping("/api/v1/loans")
class LoanController(
    private val loanService: LoanService
) {
    @Operation(summary = "대출 한도 조회", description = "신청자 정보를 기반으로 대출 한도를 조회합니다.")
    @PostMapping("/amount")
    fun checkLoanAmount(
        @Valid @RequestBody request: LoanApplicationRequest
    ): ResponseEntity<LoanApplicationResponse> {
        val response = loanService.checkLoanAmount(request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "대출 신청 상태 조회", description = "신청 ID를 기반으로 대출 신청 상태를 조회합니다.")
    @GetMapping("/{applicationId}/status")
    fun getLoanStatus(
        @Parameter(description = "대출 신청 ID", required = true)
        @PathVariable applicationId: String
    ): ResponseEntity<LoanStatusResponse> {
        val response = loanService.getLoanStatus(applicationId)
        return ResponseEntity.ok(response)
    }
}
