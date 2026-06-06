package com.mark.community.dto;

import java.util.List;

public class PostListResponse {
    private int total;
    private List<PostResponse> list;

    public int getTotal() {
        return total;
    }

    public List<PostResponse> getList() {
        return list;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setList(List<PostResponse> list) {
        this.list = list;
    }
}
