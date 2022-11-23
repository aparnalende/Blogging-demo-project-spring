package com.example.blogproject.services;

import com.example.blogproject.payloads.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDTO, Integer postId);

	void deleteComment(Integer commentId);
}
