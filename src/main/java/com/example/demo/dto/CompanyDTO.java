package com.example.demo.dto;

import com.example.demo.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {
    private Long id;
    private String ticker;
    private String name;

    // Entity를 DTO로 변환하는 생성자
    public CompanyDTO(CompanyEntity entity) {
        this.id = entity.getId();
        this.ticker = entity.getTicker();
        this.name = entity.getName();
    }

    // DTO를 Entity로 변환하는 메서드
    public CompanyEntity toEntity() {
        return CompanyEntity.builder()
            .id(this.id)
            .ticker(this.ticker)
            .name(this.name)
            .build();
    }
}
