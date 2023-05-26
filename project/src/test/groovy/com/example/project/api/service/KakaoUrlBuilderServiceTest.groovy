package com.example.project.api.service

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUrlBuilderServiceTest extends Specification {


    private KakaoUrlBuilderService kakaoUrlBuilderService;

    // def를 이용해서 테스트할 메소드를 만든다
    def setup() {   // 모든 feature메소드 전에 실행
        kakaoUrlBuilderService = new KakaoUrlBuilderService();
    }

    // 단위unit테스트
    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩"() {
        given:
        String address = "서울 성북구"
        def charset = StandardCharsets.UTF_8

        // def uri : URI ==> def키워드를 이용해서 타입을 동적으로 선언해도 가능함.
        when:
        def uri = kakaoUrlBuilderService.buildUriByAddressSearch(address);
        def decodedResult = URLDecoder.decode(uri.toString(),charset);

        then:
        // println decodedResult // sout
        decodedResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구"

    }



}
