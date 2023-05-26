package com.example.project.api.service;

import com.example.project.api.dto.kakaoApiResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoUrlBuilderService kakaoUrlBuilderService;

    @Value("${KAKAO.REST.API.KEY}")
    private String kakaoRestApiKey;

    public kakaoApiResponseDto requestAddressSerch(String address) {

        // validationcheck  ObjectUtils 사용
        if(ObjectUtils.isEmpty(address)) {
            return null;
        }

        URI uri = kakaoUrlBuilderService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        // kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, kakaoApiResponseDto.class).getBody();

    }

}
