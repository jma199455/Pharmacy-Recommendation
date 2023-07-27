package com.example.project.pharmacy.service;

import com.example.project.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.project.pharmacy.dto.PharmacyDto;
import com.example.project.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // 약국 데이터 조회
    public List<PharmacyDto> searchPharmacyDtoList() { // 전체 약국 데이터를 조회, 메소드가 호출됬을 때 먼저 redis에서 조회하고 문제가 생기면 DB에서 조화
        // redis
        List<PharmacyDto> pharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if(!pharmacyDtoList.isEmpty()) {
            log.info("redis findAll success!!");
            return pharmacyDtoList;
        }

        // db
        return pharmacyRepositoryService.findAll()

                .stream()
                .map(this::convertToPharmacyDto)// 메서드 레퍼런스 시용해서 간결하게 사용 가능 (this::convertToPharmacyDto)
                .collect(Collectors.toList());
    }

    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy) {

        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyName(pharmacy.getPharmacyName())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }
}
