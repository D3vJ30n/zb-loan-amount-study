package com.example.demo.service;

import com.example.demo.dto.DividendDTO;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DividendService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public List<DividendDTO> getDividendByCompanyName(String companyName) {
        // TODO: 배당금 정보 조회 로직
        return null;
    }
}