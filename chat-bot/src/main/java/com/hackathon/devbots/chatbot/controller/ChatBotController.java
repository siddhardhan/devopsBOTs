package com.hackathon.devbots.chatbot.controller;

import com.hackathon.devbots.chatbot.model.AWSLexAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    @PostMapping(value = "/intent", consumes = "application/json")
    public ResponseEntity<Object> getChatIntent(@RequestBody AWSLexAttributes attributes) {
        log.info("POSTED Values--->" + attributes.toString());
        return ResponseEntity.ok().build();
    }

}
