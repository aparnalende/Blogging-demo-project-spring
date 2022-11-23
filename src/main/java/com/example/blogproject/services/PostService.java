package com.example.blogproject.services;

import java.util.List;

import com.example.blogproject.entities.Post;
import com.example.blogproject.payloads.PostDTO;
import com.example.blogproject.payloads.PostResponse;

public interface PostService {

//	create
	PostDTO createPost(PostDTO pdto, Integer userId, Integer categoryId);

//	Update
	PostDTO updatePost(PostDTO pdto, Integer postId);

//delete
	void deletePost(Integer postId);

	// List
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

	// single post
	PostDTO getPostById(Integer postId);

	// all list of post by category
	List<PostDTO> getPostByCategoryId(Integer categoryId);

	// getAllPOstByUser
	List<PostDTO> getPostByUser(Integer userId);

	//
	List<PostDTO> searchPost(String keyword);
}
