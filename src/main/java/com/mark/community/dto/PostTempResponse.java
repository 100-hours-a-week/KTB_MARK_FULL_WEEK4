package com.mark.community.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostTempResponse {
    private String postId;
    private List<String> images;
    private String title;
    private String body;

    public PostTempResponse(String postId){
        this.postId = postId;
    }

    public PostTempResponse(String postId, List<String> images){
        this.postId = postId;
        this.images = images;
    }

    public PostTempResponse(String postId, String title, String body, List<String> images){
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPostId() {
        return postId;
    }
}
