package com.example.slmiddleware.contorller;

import com.example.slmiddleware.service.CommunicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunicationController {
    private final CommunicationService communicationService;

    @PostMapping("/hi")
    public void t(@RequestBody String a) throws JsonProcessingException{
        communicationService.t(a);
    }
}
