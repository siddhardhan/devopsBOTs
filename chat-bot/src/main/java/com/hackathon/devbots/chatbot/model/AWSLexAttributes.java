package com.hackathon.devbots.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AWSLexAttributes {

    @JsonProperty("channel_id")
    private String channelId;
    @JsonProperty("channel_name")
    private String channelName;
    @JsonProperty("team_domain")
    private String teamDomain;
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("token")
    private String token;
    @JsonProperty("trigger_word")
    private String triggerWord;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("fileIds")
    private String fileIds;


}
