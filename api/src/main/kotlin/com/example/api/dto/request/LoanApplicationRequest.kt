package com.example.api.dto.request

import com.example.api.dto.type.EmploymentStatus
import com.example.api.dto.type.HomeOwnership
import jakarta.validation.constraints.*

data class LoanApplicationRequest(
    @field:NotBlank(message = "이름은 필수입니다")
    @field:Size(min = 2, max = 50, message = "이름은 2~50자 사이여야 합니다")
    val name: String,

    @field:NotBlank(message = "주민등록번호는 필수입니다")
    @field:Pattern(regexp = "^[0-9]{13}$", message = "주민등록번호는 13자리 숫자여야 합니다")
    val regNo: String,

    @field:NotBlank(message = "휴대폰 번호는 필수입니다")
    @field:Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호는 10~11자리 숫자여야 합니다")
    val cellPhone: String,

    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    val email: String,

    @field:NotNull(message = "연소득은 필수입니다")
    @field:Min(value = 0, message = "연소득은 0 이상이어야 합니다")
    val income: Long,

    @field:NotNull(message = "신용점수는 필수입니다")
    @field:Min(value = 0, message = "신용점수는 0 이상이어야 합니다")
    @field:Max(value = 1000, message = "신용점수는 1000 이하여야 합니다")
    val creditScore: Int,

    @field:NotNull(message = "고용형태는 필수입니다")
    val employmentStatus: EmploymentStatus,

    @field:NotNull(message = "주택소유여부는 필수입니다")
    val homeOwnership: HomeOwnership
)