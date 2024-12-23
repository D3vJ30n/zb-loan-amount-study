package com.example.demo.web;

import com.example.demo.dto.CompanyDTO;
import com.example.demo.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<List<CompanyDTO>> autocomplete(@RequestParam String keyword) {
        var result = companyService.getCompanyNamesByKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<CompanyDTO> searchCompany(@PathVariable String ticker) {
        var result = companyService.getCompanyByTicker(ticker);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody CompanyDTO request) {
        var result = companyService.save(request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<String> deleteCompany(@PathVariable String ticker) {
        var result = companyService.deleteCompany(ticker);
        return ResponseEntity.ok(result);
    }
}
