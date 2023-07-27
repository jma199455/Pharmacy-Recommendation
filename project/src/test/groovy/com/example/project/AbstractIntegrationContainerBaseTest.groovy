package com.example.project

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest // 스프링컨테이너를 띄어서 통합테스트 환경 구성
abstract class AbstractIntegrationContainerBaseTest extends Specification{
    // 싱클 턴 컨테이너 만들기
    // 테스트가 실행될 때 환번만 실행하도록
    static final GenericContainer MY_REDIS_CONTAINER;

    static {
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
            .withExposedPorts(6379) // docker에서 노출(expose)한 포트 ==> 스프링부트가 레디스랑 통신

        MY_REDIS_CONTAINER.start();

        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())
    }



}
