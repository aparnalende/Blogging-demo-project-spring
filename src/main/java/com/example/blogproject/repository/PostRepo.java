package com.example.blogproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogproject.entities.Category;
import com.example.blogproject.entities.Post;
import com.example.blogproject.entities.User;
import com.example.blogproject.payloads.PostDTO;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category cat);
	
	List<Post> findByTitleContaining(String title);
}
