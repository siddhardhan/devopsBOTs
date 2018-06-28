package com.hackathon.devbots.chatbot.mattermost.controller;

import com.hackathon.devbots.chatbot.mattermost.model.AWSLexAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mattermost")
public class MessageController {

    @PostMapping(value = "/message", consumes = "application/json")
    public ResponseEntity<Object> getChatIntent(@RequestBody AWSLexAttributes attributes) {
        log.info("POSTED Values--->" + attributes.toString());
        return ResponseEntity.ok(attributes.toString());
    }

}
