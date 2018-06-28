package com.hackathon.devbots.chatbot.jenkins.controller;

import com.hackathon.devbots.chatbot.jenkins.model.JenkinsModel;
import com.hackathon.devbots.chatbot.jenkins.services.JenkinsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/jenkins")
public class JenkinsController {

    @Autowired
    private JenkinsService jenkinsService;

    @PostMapping(value = "/runbuild", consumes = "application/json")
    public ResponseEntity<Object> getChatIntent(@RequestBody JenkinsModel jenkinsModel) {
        log.info("POSTED Values--->" + jenkinsModel.toString());
        log.info("GET Jenkins Data--->" + jenkinsService.getJenkinsJob());
        log.info("POST Jenkins Data--->" + jenkinsService.postJenkinsJob());
        return ResponseEntity.ok(jenkinsModel.toString());
    }

}
