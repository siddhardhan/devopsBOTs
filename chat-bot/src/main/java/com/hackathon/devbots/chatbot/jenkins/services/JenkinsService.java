package com.hackathon.devbots.chatbot.jenkins.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class JenkinsService {

    @Autowired
    private RestTemplate restTemplate;

    public String runJenkinsJob() {
        return restTemplate.getForObject(
                "http://gturnquist-quoters.cfapps.io/api/random", String.class);
    }
}
