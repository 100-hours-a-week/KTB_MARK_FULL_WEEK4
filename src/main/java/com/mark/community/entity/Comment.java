package com.mark.community.entity;

import java.util.Date;

public class Comment {
    private String commentId;
    private String postId;
    private String userId;
    private String comment;
    private String nickname;
    private boolean deleted = false;
    private String parentCommentId;
    private Date commentTime;


    public Comment(String postId, String userId, String comment, Date commentTime, String nickName, String parentCommentId){
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.commentTime = commentTime;
        this.nickname = nickName;
        this.parentCommentId = parentCommentId;

    }

    public Comment(){

    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }


    public String getNickname() {
        return nickname;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public String getPostId() {
        return postId;
    }

    public String getComment() {
        return comment;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}
