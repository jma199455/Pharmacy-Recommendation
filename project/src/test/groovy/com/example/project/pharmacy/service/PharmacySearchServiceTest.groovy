package com.example.project.pharmacy.service

import com.example.project.pharmacy.cache.PharmacyRedisTemplateService
import com.example.project.pharmacy.entity.Pharmacy
import com.google.common.collect.Lists
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService;

    // Mock() 키워드를 이용한 모킹
    private  PharmacyRepositoryService pharmacyRepositoryService = Mock();
    private  PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock();

    private List<Pharmacy> pharmacyList;

    def setup(){
        pharmacySearchService = new PharmacySearchService(pharmacyRepositoryService, pharmacyRedisTemplateService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        )
    }

    def "레디스 장애시 DB를 이용하여 약국 데이터 조회"() {

        when:
        pharmacyRedisTemplateService.findAll() >> [] // 어떤 리턴 값을 리턴할지를 정의하는 것이라고 생각
        pharmacyRepositoryService.findAll() >> pharmacyList

        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2

    }






}
