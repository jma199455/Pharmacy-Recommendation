package com.example.project.pharmacy.controller;

import com.example.project.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.project.pharmacy.dto.PharmacyDto;
import com.example.project.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor

// DB데이터를 redis로 저장할 수 있는 메소드 구현
public class PharmacyController {

    // db에서 조회하는 서비스
    private final PharmacyRepositoryService pharmacyRepositoryService;
    // redis에서 조회하는 서비스
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // 데이터 초기 세팅을 위한 임시 메소드
    @GetMapping("/redis/save")
    public String save() {

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build())
                .collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";

    }


}
