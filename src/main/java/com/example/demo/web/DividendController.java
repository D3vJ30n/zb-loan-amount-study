package com.example.demo.web;

import com.example.demo.service.DividendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dividend")
@AllArgsConstructor
public class DividendController {

    private final DividendService dividendService;

    @GetMapping("/{companyName}")
    public ResponseEntity<?> getDividendByCompanyName(@PathVariable String companyName) {
        var result = dividendService.getDividendByCompanyName(companyName);
        return ResponseEntity.ok(result);
    }
}