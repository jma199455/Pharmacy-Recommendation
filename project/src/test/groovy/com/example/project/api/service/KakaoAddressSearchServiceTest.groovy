package com.example.project.api.service

import com.example.project.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

// AbstractIntegrationContainerBaseTest (테스트 컨테이너를 이용한 전체 통합 테스트 클래스)
class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    def "address 파리미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다"() {
        given:
        String address = null // 도 가능 !! def address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소값이 valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다."() {

        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null

    }



}
