package com.example.blogproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogproject.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
