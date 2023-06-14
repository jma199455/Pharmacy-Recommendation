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

    // 전체 약국 데이터를 조회하는 메소드
    public List<PharmacyDto> searchPharmacyDtoList() {


        // redis 사용 empty 리스트가 아닐경우
        List<PharmacyDto> PharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if(!PharmacyDtoList.isEmpty()) {
            return PharmacyDtoList;
        }

        // 비어있다면 db사용으로 데이터 가져오기
        // db
        return pharmacyRepositoryService.findAll()

                .stream()
                .map(this::convertToPharmacyDto)// 메서드 레퍼런스 시용해서 간결하게 사용 가능 (this::convertToPharmacyDto)
                .collect(Collectors.toList());

        // DB데이터를 조회할 경우 해당 데이터를 redis에 동일하게 동기화 해줘야함 (controller에)
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
