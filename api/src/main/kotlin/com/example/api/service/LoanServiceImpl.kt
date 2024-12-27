package com.example.api.service.impl

import com.example.api.dto.request.LoanApplicationRequest
import com.example.api.dto.response.LoanApplicationResponse
import com.example.api.dto.response.LoanStatusResponse
import com.example.api.dto.type.EmploymentStatus
import com.example.api.dto.type.HomeOwnership
import com.example.api.dto.type.LoanStatus
import com.example.api.service.LoanService
import com.example.domain.entity.LoanApplication
import com.example.domain.repository.LoanRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class LoanServiceImpl(
    private val loanRepository: LoanRepository
) : LoanService {

    @Transactional
    override fun checkLoanAmount(request: LoanApplicationRequest): LoanApplicationResponse {
        // 대출 한도 계산 로직
        val loanLimit = calculateLoanLimit(
            income = request.income,
            creditScore = request.creditScore,
            employmentStatus = request.employmentStatus,
            homeOwnership = request.homeOwnership
        )

        // 금리 계산 로직
        val interestRate = calculateInterestRate(
            creditScore = request.creditScore,
            employmentStatus = request.employmentStatus
        )

        // 대출 기간 설정 (기본 12개월)
        val loanTerm = 12

        // 신청 정보 저장
        val application = LoanApplication(
            applicationId = UUID.randomUUID().toString(),
            name = request.name,
            regNo = request.regNo,
            cellPhone = request.cellPhone,
            email = request.email,
            income = request.income,
            creditScore = request.creditScore,
            employmentStatus = request.employmentStatus.name,
            homeOwnership = request.homeOwnership.name,
            loanLimit = loanLimit,
            interestRate = interestRate,
            loanTerm = loanTerm,
            status = LoanStatus.PENDING.name,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val savedApplication = loanRepository.save(application)

        return LoanApplicationResponse(
            loanLimit = savedApplication.loanLimit,
            interestRate = savedApplication.interestRate,
            loanTerm = savedApplication.loanTerm,
            applicationId = savedApplication.applicationId,
            createdAt = savedApplication.createdAt
        )
    }

    @Transactional(readOnly = true)
    override fun getLoanStatus(applicationId: String): LoanStatusResponse {
        val application = loanRepository.findByApplicationId(applicationId)
            ?: throw NoSuchElementException("대출 신청 정보를 찾을 수 없습니다: $applicationId")

        return LoanStatusResponse(
            applicationId = application.applicationId,
            status = LoanStatus.valueOf(application.status),
            updatedAt = application.updatedAt,
            message = getStatusMessage(LoanStatus.valueOf(application.status))
        )
    }

    private fun calculateLoanLimit(
        income: Long,
        creditScore: Int,
        employmentStatus: EmploymentStatus,
        homeOwnership: HomeOwnership
    ): Long {
        // 기본 대출 한도는 연봉의 200%
        var loanLimit = income * 2

        // 신용점수에 따른 조정
        loanLimit = when {
            creditScore >= 800 -> (loanLimit * 1.5).toLong()  // 우수 신용
            creditScore >= 600 -> loanLimit                   // 일반 신용
            else -> (loanLimit * 0.5).toLong()               // 저신용
        }

        // 고용 상태에 따른 조정
        loanLimit = when (employmentStatus) {
            EmploymentStatus.EMPLOYED -> loanLimit            // 정규직
            EmploymentStatus.SELF_EMPLOYED -> (loanLimit * 0.8).toLong() // 자영업자
            EmploymentStatus.OTHER -> (loanLimit * 0.5).toLong() // 기타
        }

        // 주택 소유 여부에 따른 조정
        loanLimit = when (homeOwnership) {
            HomeOwnership.OWNED -> (loanLimit * 1.2).toLong() // 자가 소유
            HomeOwnership.RENT -> loanLimit                   // 임차
            HomeOwnership.OTHER -> (loanLimit * 0.8).toLong() // 기타
        }

        return loanLimit
    }

    private fun calculateInterestRate(
        creditScore: Int,
        employmentStatus: EmploymentStatus
    ): Double {
        // 기본 금리 5%
        var rate = 5.0

        // 신용점수에 따른 금리 조정
        rate += when {
            creditScore >= 800 -> -2.0  // 우수 신용
            creditScore >= 600 -> 0.0   // 일반 신용
            else -> 2.0                 // 저신용
        }

        // 고용 상태에 따른 금리 조정
        rate += when (employmentStatus) {
            EmploymentStatus.EMPLOYED -> 0.0      // 정규직
            EmploymentStatus.SELF_EMPLOYED -> 1.0 // 자영업자
            EmploymentStatus.OTHER -> 2.0         // 기타
        }

        return rate.coerceIn(1.0, 15.0) // 최소 1%, 최대 15%
    }

    private fun getStatusMessage(status: LoanStatus): String {
        return when (status) {
            LoanStatus.PENDING -> "대출 심사가 진행 중입니다."
            LoanStatus.APPROVED -> "대출이 승인되었습니다."
            LoanStatus.REJECTED -> "대출이 거절되었습니다."
        }
    }
}