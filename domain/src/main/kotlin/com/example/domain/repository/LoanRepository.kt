package com.example.domain.repository

import com.example.domain.entity.LoanApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository : JpaRepository<LoanApplication, Long> {
    fun findByApplicationId(applicationId: String): LoanApplication?
    fun existsByRegNo(regNo: String): Boolean
    fun findAllByOrderByCreatedAtDesc(): List<LoanApplication>
}