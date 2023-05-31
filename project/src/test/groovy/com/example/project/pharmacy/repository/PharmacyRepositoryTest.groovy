package com.example.project.pharmacy.repository

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

// 통합 테스트 환경 구성
// 테스트컨테이너를 이용한 통합 테스트 환경이기 때문에 테스트 케이스 실행 전 도커가 실행되어야 한다.
class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    // @Autowired 스프링 컨테이너에 의해서 bean 주입받을 수 있음
    @Autowired
    private PharmacyRepository pharmacyRepository;

    // 테스트 메소드 시작 전 실행되는 메소드
    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository save"() {
        given:
        String address = "서울 특별시 성북구 중앙동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy =  Pharmacy.builder()
            .pharmacyAddress(address)
            .pharmacyName(name)
            .latitude(latitude)
            .longitude(longitude)
            .build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude

    }

    def "PharmacyRepository saveAll" () {
        given:
        String address = "서울 특별시 성북구 중앙동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy =  Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        pharmacyRepository.saveAll(Arrays.asList(pharmacy))
        def result = pharmacyRepository.findAll()

        then:
        result.size() == 1
    }

    def "BaseTimeEntity 등록"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
            .pharmacyAddress(address)
            .pharmacyName(name)
            .build()

        when:
        pharmacyRepository.save(pharmacy)
        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)

    }


}
