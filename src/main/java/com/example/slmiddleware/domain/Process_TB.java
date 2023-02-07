package com.example.slmiddleware.domain;

import jakarta.persistence.*; // JPA API를 가져옴
import lombok.AllArgsConstructor; // 모든 필드를 포함한 생성자 생성
import lombok.Getter; // getter 메소드 자동 생성
import lombok.NoArgsConstructor; // 매개변수 없는 생성자 생성
import lombok.Setter;
import oracle.sql.DATE;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity // JPA에서 관리하는 객체로 매핑
@Getter // getter 메소드 자동 생성
@Setter
@AllArgsConstructor // 모든 필드를 포함한 생성자 생성
@NoArgsConstructor // 매개변수 없는 생성자 생성
public class Process_TB extends BaseTimeEntity {
    @Id // 기본 키
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR") // 시퀀스 생성 전략
//    @SequenceGenerator(name = "xxx_SEQUENCE_GENERATOR", sequenceName = "xxx_SEQUENCE", initialValue = 1, allocationSize = 1) // 시퀀스 생성 정보
    @GenericGenerator(
            name = "PROCESS_seq",
            strategy = "com.vladmihalcea.hibernate.id.BatchSequenceGenerator",
            parameters = {@Parameter(name = "sequence", value = "PROCESS_seq"), @Parameter(name = "fetch_size", value = "1000")
            }
    )

    private Long PRC_SQ; // 고유 번호


    @Column
    private String WKCTR_CD;
    private String PRC_CD_01;
    private int QUALITY_01;
    private DATE CREATE_DT_01;
    private int DATA_A_01;
    private int DATA_B_01;
    private DATE END_DT_01;

    private String PRC_CD_02;
    private int QUALITY_02;
    private DATE CREATE_DT_02;
    private int DATA_A_02;
    private int DATA_B_02;
    private DATE END_DT_02;

    private String PRC_CD_03;
    private int QUALITY_03;
    private DATE CREATE_DT_03;
    private int DATA_A_03;
    private int DATA_B_03;
    private DATE END_DT_03;

    private String PRC_CD_04;
    private int QUALITY_04;
    private DATE CREATE_DT_04;
    private int DATA_A_04;
    private int DATA_B_04;
    private DATE END_DT_04;

    private String PRC_CD_05;
    private int QUALITY_05;
    private DATE CREATE_DT_05;
    private int DATA_A_05;
    private int DATA_B_05;
    private DATE END_DT_05;

    private String PRC_CD_06;
    private int QUALITY_06;
    private DATE CREATE_DT_06;
    private int DATA_A_06;
    private int DATA_B_06;
    private DATE END_DT_06;

}
