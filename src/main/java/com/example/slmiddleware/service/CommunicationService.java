package com.example.slmiddleware.service;

import com.example.slmiddleware.domain.Process;
import com.example.slmiddleware.domain.ProcessMapper;
import com.example.slmiddleware.domain.ProcessRepository;
import com.example.slmiddleware.dto.ResponseProcessMsgDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.json.JsonObject;
import org.json.JSONException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;
@Service // 스프링에서의 빈 객체로 사용되는 어노테이션
@RequiredArgsConstructor // final로 선언된 필드만을 이용해서 생성자를 만드는 어노테이션
public class CommunicationService {
    private final ProcessRepository processRepository; // 필수 필드인 ProcessRepository 객체
    private final Queue<Process> queue = new LinkedList<>();// 프로세스를 저장할 큐



    public void test(Object message) throws JsonProcessingException, JSONException {
        JSONObject object = new JSONObject(message);  // JSON 객체로 변환
        String errcode = object.get("ERR_CD").toString();  // ERR_CD 값 추출

        ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper 객체 생성
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 기본 값인 true에서 false로 변경, 선언한 필드만 매핑
        String jsonSting = objectMapper.writeValueAsString(message);
        // 객체를 JSON 문자열로 변환
        ResponseProcessMsgDto msg[] = objectMapper.readValue(jsonSting,ResponseProcessMsgDto[].class);
        // 문자열을 ResponseProcessMsgDto 배열로 변환
        System.out.println(message);  // 메시지 출력

        Process process = ProcessMapper.MAPPER.toEntity(msg[0]);  // msg[0]을 Process 객체로 변환
        queue.add(process);  // 큐에 추가

        if(queue.size() >= 50) {  // 큐의 크기가 50 이상일 때
            processRepository.saveAll(queue);  // 큐에 있는 모든 프로세스 저장
            queue.clear();  // 큐 초기화
            // saveall이 아니라 union all 쿼리문 작성해서 jpa 함수만든다.
        }
    }

    public void t(){

    }

}