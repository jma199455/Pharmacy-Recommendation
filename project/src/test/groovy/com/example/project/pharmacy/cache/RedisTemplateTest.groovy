package com.example.project.pharmacy.cache

import com.example.project.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations
import org.springframework.data.redis.core.ValueOperations

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate redisTemplate // redis템플릿 사용

    def "RedisTemplate String operations"() {   // String 자료구조 사용

        given:

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue()
        String key = "stringKey"
        String value = "hello"

        when:
        valueOperations.set(key, value) // key value 저장

        then:
        String resultValue = valueOperations.get(key) // 조회
        resultValue == "hello"
    }

    def "RedisTemplate sort operations"() {
        given:
        SetOperations<String, String> setOperations = redisTemplate.opsForSet()
        String key = "setKey"

        when:
        setOperations.add(key, "h", "e", "l", "l", "o") // set 자료구조는 중복값 자동 제거해줌

        Set<String> members = setOperations.members(key)
        Long size = setOperations.size(key)

        then:

        members.containsAll(["h","e","l","o"])
        size == 4
    }

    // hash 자료구조 테스트케이스
    def "RedisTemplate hash operations"() {
        given:
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash() // key에 대응하는 여러개의 필드 value 가 존재한다.
        String key = "hashKey"

        when:
        hashOperations.put(key, "subKey", "value")  // 저장

        then:
        String value = hashOperations.get(key, "subKey")
        value == "value"

        Map<String, String> entries = hashOperations.entries(key)
        entries.keySet().contains("subKey")
        entries.values().contains("value")

        Long size = hashOperations.size(key)
        size == entries.size()
    }
}