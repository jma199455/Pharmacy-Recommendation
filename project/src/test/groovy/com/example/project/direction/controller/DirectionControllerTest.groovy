package com.example.project.direction.controller

import com.example.project.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc;

    private DirectionService directionService = Mock() // Mock() 키워드로 모킹

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService))
                .build()
    }

    // redirect가 정상적으로 되는지 테스트 진행

    def "GET /dir/{encodedId}"() {
        given:
        String encodedId = "r"

        String redirectURL = "https://map.kakao.com/link/map/pharmacy,38.11,128.11"

        when:
        directionService.findDirectionUrlById(encodedId) >> redirectURL // spock에서 모킹 >> (어떤값을 리턴할것인지)

        def result = mockMvc.perform (MockMvcRequestBuilders.get("/dir/{encodedId}", encodedId))

        then:
        result.andExpect(status().is3xxRedirection())  // 302 리다이렉트 발생 확인
                .andExpect(redirectedUrl(redirectURL)) // 리다이렉트 경로 검증
                .andDo(print())
    }



}
