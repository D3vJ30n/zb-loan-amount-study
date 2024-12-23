package com.example.demo.repository;

import com.example.demo.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    // 회사 ID로 배당금 정보 조회
    List<DividendEntity> findAllByCompanyId(Long companyId);

    // 회사 ID로 배당금 정보 삭제 (트랜잭션 처리)
    @Transactional
    void deleteAllByCompanyId(Long companyId);

    // 회사 ID와 배당금 지급 날짜로 배당금 정보 조회
    Optional<DividendEntity> findByCompanyIdAndDate(Long companyId, LocalDateTime date);

    // 특정 날짜 범위 내의 배당금 정보 조회
    List<DividendEntity> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 특정 회사의 특정 날짜 범위 내의 배당금 정보 조회
    List<DividendEntity> findByCompanyIdAndDateBetween(Long companyId, LocalDateTime startDate, LocalDateTime endDate);

    // 배당금 정보 중복 체크
    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
