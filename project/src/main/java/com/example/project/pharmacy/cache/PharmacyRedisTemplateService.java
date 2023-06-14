package com.example.project.pharmacy.cache;

import com.example.project.pharmacy.dto.PharmacyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRedisTemplateService { // redis CRUD 구현

    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;   // config에 bean 등록해서 사용(싱클톤 사용)

    private HashOperations<String, String, String> hashOperations;

    // hash자료구조 사용 데이터 관리
    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();   // redistemplate에서 제공하는 hash자료구조
    }

    // redis 저장 메소드
    public void save(PharmacyDto pharmacyDto) {
        if(Objects.isNull(pharmacyDto) || Objects.isNull(pharmacyDto.getId())) {
            log.error("Required Values must be not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    pharmacyDto.getId().toString(),
                    serializePharmacyDto(pharmacyDto));
            log.info("[PharmacyRedisTemplateService save success] id: {}", pharmacyDto.getId());
        } catch (Exception e) {
            log.info("[PharmacyRedisTemplateService save error] {}", e.getMessage());
        }
    }

    // 전체 조회 메소드
    public List<PharmacyDto> findAll() {
        try {
            List<PharmacyDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) { // hash자료구조 entries사용으로 value값 가져오기
                PharmacyDto pharmacyDto = deserializePharmacyDto(value);
                list.add(pharmacyDto);
            }
            return list;
        } catch (Exception e) {
            log.error("[PharmacyRedisTemplateService findAll error] : {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    // delete 메소드
    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[PharmacyRedisTemplateService delete] : {} ", id);
    }

    // 객체 dto로담기 -> json형식 (serialize메소드)
    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pharmacyDto);
    }

    // json형식 -> 객체 dto로담기 (serialize메소드)
    private PharmacyDto deserializePharmacyDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, PharmacyDto.class);
    }




}
