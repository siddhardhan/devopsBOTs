package com.hackathon.devbots.chatbot.jenkins.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JenkinsModel {

    @JsonProperty("channel_id")
    private String channelId;

}
