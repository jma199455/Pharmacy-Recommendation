package com.example.project.pharmacy.service;

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

    private PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyDtoList() {

        // redis

        // db
        return pharmacyRepositoryService.findAll()
                .stream()
                .map(entity -> convertToPharmacyDto(entity))// 메서드 레퍼런스 시용해서 간결하게 사용 가능 (this::convertToPharmacyDto)
                .collect(Collectors.toList());
    }


    // 컨버팅
    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy) {
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }
}
