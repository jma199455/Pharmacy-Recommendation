package com.example.project.direction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "direction") // direction 테이블과 매핑??
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class Direction {

    @Id             // 매핑될 pk 값
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스가 알아서 pk 값 생성 후 매핑
    private Long id;

    // 고객 데이터
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 약국 데이터
    private String targetPharmacyName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 고객 주소 와 약국 주소 사이의 거리
    private double distance;


}
