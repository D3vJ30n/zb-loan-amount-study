package com.example.domain.repository

import com.example.domain.entity.LoanApplication
import org.springframework.data.jpa.repository.JpaRepository

interface LoanApplicationRepository : JpaRepository<LoanApplication, Long>
