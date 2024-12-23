package com.example.demo.service;

import com.example.demo.dto.CompanyDTO;
import com.example.demo.entity.CompanyEntity;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Transactional
    public CompanyDTO save(CompanyDTO companyDto) {
        CompanyEntity company = CompanyEntity.builder()
            .ticker(companyDto.getTicker())
            .name(companyDto.getName())
            .build();

        CompanyEntity savedCompany = companyRepository.save(company);
        return new CompanyDTO(savedCompany);
    }

    public List<CompanyDTO> getCompanyNamesByKeyword(String keyword) {
        // 키워드로 회사 검색 구현
        return companyRepository.findAll().stream()
            .filter(company -> company.getName().contains(keyword))
            .map(CompanyDTO::new)
            .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyByTicker(String ticker) {
        CompanyEntity company = companyRepository.findByTicker(ticker);
        return new CompanyDTO(company);
    }

    @Transactional
    public String deleteCompany(String ticker) {
        CompanyEntity company = companyRepository.findByTicker(ticker);
        if (company != null) {
            dividendRepository.deleteAllByCompanyId(company.getId());
            companyRepository.delete(company);
            return "회사 정보가 삭제되었습니다.";
        }
        return "회사를 찾을 수 없습니다.";
    }
}
