package com.example.project.pharmacy.entity;

import com.example.project.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "pharmacy") // jpa 사용 데이터 베이스와 매핑될 컬럼 정의(해당 클래스 이름)
@Getter
@Builder // test에서 사용??
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends BaseTimeEntity {

    @Id             // 매핑될 pk 값
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스가 알아서 pk 값 생성 후 매핑
    private Long id;

    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    public void changePharmacyAddress(String address) {

        this.pharmacyAddress = address;
    }



}
