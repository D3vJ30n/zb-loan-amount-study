package com.example.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "loan_applications")
class LoanApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, unique = true)
    val applicationId: String,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false)
    val regNo: String,

    @Column(nullable = false)
    val cellPhone: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val income: Long,

    @Column(nullable = false)
    val creditScore: Int,

    @Column(nullable = false, length = 20)
    val employmentStatus: String,

    @Column(nullable = false, length = 20)
    val homeOwnership: String,

    @Column(nullable = false)
    val loanLimit: Long,

    @Column(nullable = false)
    val interestRate: Double,

    @Column(nullable = false)
    val loanTerm: Int,

    @Column(nullable = false, length = 20)
    var status: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    var updatedAt: LocalDateTime
) {
    fun updateStatus(newStatus: String) {
        this.status = newStatus
        this.updatedAt = LocalDateTime.now()
    }
}