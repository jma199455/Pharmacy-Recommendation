package com.example.project.pharmacy.cache

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.pharmacy.dto.PharmacyDto
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // 각각 테스트 메소드마다 의존성이 생기지 않게 테스트 시작전에 redis 비워주고 시작하기 위한 설정
    def setup() {
        pharmacyRedisTemplateService.findAll()
            .forEach(dto -> {
                pharmacyRedisTemplateService.delete(dto.getId())
            })
    }

    // 저장이 성공
    def "save success"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto =
                    PharmacyDto.builder()
                            .id(1L)
                            .pharmacyName(pharmacyName)
                            .pharmacyAddress(pharmacyAddress)
                            .build()

        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()
        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).pharmacyName == pharmacyName
        result.get(0).pharmacyAddress == pharmacyAddress
    }

    // 저장이 실패
    def "success fail"() {
        given:
        PharmacyDto dto = PharmacyDto.builder()
                            .build()
        when:
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()
        then:
        result.size() == 0
    }

    def "delete"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto =
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName(pharmacyName)
                        .pharmacyAddress(pharmacyAddress)
                        .build()
        when:
        pharmacyRedisTemplateService.save(dto)
        pharmacyRedisTemplateService.delete(dto.getId())
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

}
