package com.hackathon.devbots.chatbot.jenkins.services;

import com.hackathon.devbots.chatbot.mattermost.model.AWSLexAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class JenkinsService {

    @Autowired
    private RestTemplate restTemplate;

    // making a get call
    public String getJenkinsJob() {
        return restTemplate.getForObject(
                "http://gturnquist-quoters.cfapps.io/api/random", String.class);
    }

    // making a post call
    public String postJenkinsJob() {
        return restTemplate.postForObject(
                "http://localhost:8081/mattermost/message",
                AWSLexAttributes.builder()
                        .channelName("test")
                        .channelId("test")
                        .build(),
                String.class);
    }
}
