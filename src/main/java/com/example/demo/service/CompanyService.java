package com.example.demo.service;

import com.example.demo.dto.CompanyDTO;
import com.example.demo.entity.CompanyEntity;
import com.example.demo.entity.DividendEntity;
import com.example.demo.model.ScrapedResult;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DividendRepository;
import com.example.demo.scraper.YahooFinanceScraper;
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
    private final YahooFinanceScraper yahooFinanceScraper;

    @Transactional
    public CompanyDTO save(String ticker) {
        // 회사 존재 여부 확인
        boolean exists = companyRepository.existsByTicker(ticker);
        if (exists) {
            throw new RuntimeException("이미 존재하는 회사입니다.");
        }

        // 야후 파이낸스에서 회사의 정보를 스크래핑
        ScrapedResult scrapedResult = yahooFinanceScraper.scrap(ticker);
        
        // 회사 정보 저장
        CompanyEntity company = companyRepository.save(
            CompanyEntity.builder()
                .ticker(scrapedResult.getCompany().getTicker())
                .name(scrapedResult.getCompany().getName())
                .build()
        );

        // 배당금 정보 저장
        List<DividendEntity> dividendEntities = scrapedResult.getDividends().stream()
                .map(dividend -> DividendEntity.builder()
                        .company(company)
                        .date(dividend.getDate())
                        .dividend(dividend.getDividend())
                        .build())
                .collect(Collectors.toList());
        dividendRepository.saveAll(dividendEntities);

        return new CompanyDTO(company);
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
        if (company == null) {
            throw new RuntimeException("존재하지 않는 회사입니다.");
        }
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