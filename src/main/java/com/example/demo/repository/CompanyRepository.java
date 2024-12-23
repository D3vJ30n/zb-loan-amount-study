package com.example.demo.repository;

import com.example.demo.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Repository를 구현하는 클래스
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker); // ticker 중복 체크
    CompanyEntity findByTicker(String ticker); // ticker로 회사 정보 조회
    CompanyEntity findByName(String name); // 회사명으로 회사 정보 조회
}
