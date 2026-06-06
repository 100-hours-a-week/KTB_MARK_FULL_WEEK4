package com.mark.community.dto;

public class Counts {
    private int likes;
    private int comments;
    private int views;

    public Counts(int likes, int comments, int views) {
        this.likes = likes;
        this.comments = comments;
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getViews() {
        return views;
    }
}
