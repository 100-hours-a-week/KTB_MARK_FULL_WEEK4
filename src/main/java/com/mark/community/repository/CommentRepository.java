package com.mark.community.repository;

import com.mark.community.entity.Comment;

import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById (String commentId);
    Comment save(Comment comment);
}
