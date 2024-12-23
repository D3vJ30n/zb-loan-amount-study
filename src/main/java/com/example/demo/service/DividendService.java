package com.example.demo.service;

import com.example.demo.dto.DividendDTO;
import com.example.demo.entity.CompanyEntity;
import com.example.demo.entity.DividendEntity;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DividendService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Transactional(readOnly = true)
    public List<DividendDTO> getDividendByCompanyName(String companyName) {
        // 회사 정보 조회
        CompanyEntity company = companyRepository.findByName(companyName);
        if (company == null) {
            throw new RuntimeException("존재하지 않는 회사명입니다: " + companyName);
        }

        // 배당금 정보 조회
        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(company.getId());

        // 엔티티를 DTO로 변환
        return dividendEntities.stream()
                .map(e -> new DividendDTO(e.getDate(), e.getDividend()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DividendDTO> getDividendByCompanyAndDate(String companyName, LocalDateTime startDate, LocalDateTime endDate) {
        // 회사 정보 조회
        CompanyEntity company = companyRepository.findByName(companyName);
        if (company == null) {
            throw new RuntimeException("존재하지 않는 회사명입니다: " + companyName);
        }

        // 특정 기간의 배당금 정보 조회
        List<DividendEntity> dividendEntities = dividendRepository
                .findByCompanyIdAndDateBetween(company.getId(), startDate, endDate);

        // 엔티티를 DTO로 변환
        return dividendEntities.stream()
                .map(e -> new DividendDTO(e.getDate(), e.getDividend()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveDividend(String companyName, DividendDTO dividendDTO) {
        // 회사 정보 조회
        CompanyEntity company = companyRepository.findByName(companyName);
        if (company == null) {
            throw new RuntimeException("존재하지 않는 회사명입니다: " + companyName);
        }

        // 배당금 정보 중복 체크
        boolean exists = dividendRepository.existsByCompanyIdAndDate(company.getId(), dividendDTO.getDate());
        if (exists) {
            throw new RuntimeException("이미 존재하는 배당금 정보입니다.");
        }

        // 배당금 정보 저장
        DividendEntity dividend = DividendEntity.builder()
                .company(company)
                .date(dividendDTO.getDate())
                .dividend(dividendDTO.getDividend())
                .build();

        dividendRepository.save(dividend);
    }
}