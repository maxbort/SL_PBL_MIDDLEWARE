package com.example.slmiddleware.domain;

import com.example.slmiddleware.dto.ResponseProcessMsgDto;
import com.example.slmiddleware.service.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// 매퍼 인터페이스에 @Mapper 어노테이션 추가
@Mapper
public interface ProcessMapper extends EntityMapper<Process_TB, ResponseProcessMsgDto> {

    // 매퍼 구현체에 접근할 수 있는 MAPPER 정적 변수 생성
    ProcessMapper MAPPER = Mappers.getMapper(ProcessMapper.class);

    // Process 객체로 매핑하는 toEntity 메서드 정의
    Process_TB toEntity(final ResponseProcessMsgDto dto);
}
