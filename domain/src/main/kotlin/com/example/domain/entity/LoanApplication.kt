package com.example.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "loan_applications")
data class LoanApplication(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val applicantName: String,
    val amount: Double,
    val status: String = "PENDING",
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
