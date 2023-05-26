package com.example.project.api.service

import com.example.project.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification


// AbstractIntegrationContainerBaseTest (테스트 컨테이너를 이용한 전체 통합 테스트 클래스)
class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    def "address 파리미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다"() {
        given:
        String address = null // 도 가능 !! def address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSerch(address)

        then:
        result == null
    }

    def "주소값이 valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다."() {






    }




}
