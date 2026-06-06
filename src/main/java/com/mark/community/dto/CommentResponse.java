package com.mark.community.dto;

public class CommentResponse {
    private String commentId;

    public CommentResponse(String commentId){
        this.commentId = commentId;
    }

    public String getCommentId() {
        return commentId;
    }
}
