package com.example.api.controller

import com.example.api.dto.LoanApplicationDto
import com.example.domain.service.LoanApplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/loans")
class LoanApplicationController(private val loanApplicationService: LoanApplicationService) {

    @PostMapping
    fun createLoan(@RequestBody loanApplicationDto: LoanApplicationDto): ResponseEntity<String> {
        loanApplicationService.createLoan(loanApplicationDto.toEntity())
        return ResponseEntity.ok("Loan application submitted successfully")
    }

    @GetMapping("/{id}")
    fun getLoanById(@PathVariable id: Long): ResponseEntity<LoanApplicationDto> {
        val loan = loanApplicationService.getLoanById(id)
        return ResponseEntity.ok(LoanApplicationDto.fromEntity(loan))
    }
}
