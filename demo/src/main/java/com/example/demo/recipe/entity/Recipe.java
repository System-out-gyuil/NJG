package com.example.demo.recipe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 레시피 일련번호 (API의 RCP_SEQ)
    @Column(unique = true, nullable = false)
    private String rcpSeq;

    // 메뉴명
    private String rcpNm;

    // 조리방법
    private String rcpWay2;

    // 요리종류
    private String rcpPat2;

    // 중량(1인분)
    private String infoWgt;

    // 열량
    private String infoEng;

    // 탄수화물
    private String infoCar;

    // 단백질
    private String infoPro;

    // 지방
    private String infoFat;

    // 나트륨
    private String infoNa;

    // 해쉬태그
    @Column(columnDefinition = "TEXT")
    private String hashTag;

    // 이미지경로(소)
    @Column(columnDefinition = "TEXT")
    private String attFileNoMain;

    // 이미지경로(대)
    @Column(columnDefinition = "TEXT")
    private String attFileNoMk;

    // 재료정보
    @Column(columnDefinition = "TEXT")
    private String rcpPartsDtls;

    // 만드는법_01 ~ 20
    @Column(columnDefinition = "TEXT")
    private String manual01;
    @Column(columnDefinition = "TEXT")
    private String manualImg01;
    
    @Column(columnDefinition = "TEXT")
    private String manual02;
    @Column(columnDefinition = "TEXT")
    private String manualImg02;
    
    @Column(columnDefinition = "TEXT")
    private String manual03;
    @Column(columnDefinition = "TEXT")
    private String manualImg03;
    
    @Column(columnDefinition = "TEXT")
    private String manual04;
    @Column(columnDefinition = "TEXT")
    private String manualImg04;
    
    @Column(columnDefinition = "TEXT")
    private String manual05;
    @Column(columnDefinition = "TEXT")
    private String manualImg05;
    
    @Column(columnDefinition = "TEXT")
    private String manual06;
    @Column(columnDefinition = "TEXT")
    private String manualImg06;
    
    @Column(columnDefinition = "TEXT")
    private String manual07;
    @Column(columnDefinition = "TEXT")
    private String manualImg07;
    
    @Column(columnDefinition = "TEXT")
    private String manual08;
    @Column(columnDefinition = "TEXT")
    private String manualImg08;
    
    @Column(columnDefinition = "TEXT")
    private String manual09;
    @Column(columnDefinition = "TEXT")
    private String manualImg09;
    
    @Column(columnDefinition = "TEXT")
    private String manual10;
    @Column(columnDefinition = "TEXT")
    private String manualImg10;
    
    @Column(columnDefinition = "TEXT")
    private String manual11;
    @Column(columnDefinition = "TEXT")
    private String manualImg11;
    
    @Column(columnDefinition = "TEXT")
    private String manual12;
    @Column(columnDefinition = "TEXT")
    private String manualImg12;
    
    @Column(columnDefinition = "TEXT")
    private String manual13;
    @Column(columnDefinition = "TEXT")
    private String manualImg13;
    
    @Column(columnDefinition = "TEXT")
    private String manual14;
    @Column(columnDefinition = "TEXT")
    private String manualImg14;
    
    @Column(columnDefinition = "TEXT")
    private String manual15;
    @Column(columnDefinition = "TEXT")
    private String manualImg15;
    
    @Column(columnDefinition = "TEXT")
    private String manual16;
    @Column(columnDefinition = "TEXT")
    private String manualImg16;
    
    @Column(columnDefinition = "TEXT")
    private String manual17;
    @Column(columnDefinition = "TEXT")
    private String manualImg17;
    
    @Column(columnDefinition = "TEXT")
    private String manual18;
    @Column(columnDefinition = "TEXT")
    private String manualImg18;
    
    @Column(columnDefinition = "TEXT")
    private String manual19;
    @Column(columnDefinition = "TEXT")
    private String manualImg19;
    
    @Column(columnDefinition = "TEXT")
    private String manual20;
    @Column(columnDefinition = "TEXT")
    private String manualImg20;

    // 저감 조리법 TIP
    @Column(columnDefinition = "TEXT")
    private String rcpNaTip;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
