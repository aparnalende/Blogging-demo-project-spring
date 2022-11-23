package com.example.blogproject.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogproject.entities.Category;
import com.example.blogproject.entities.Post;
import com.example.blogproject.entities.User;
import com.example.blogproject.exceptions.ResourceNotFoundException;
import com.example.blogproject.payloads.PostDTO;
import com.example.blogproject.payloads.PostResponse;
import com.example.blogproject.repository.CategoryRepo;
import com.example.blogproject.repository.PostRepo;
import com.example.blogproject.repository.UserRepo;
import com.example.blogproject.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDTO createPost(PostDTO pdto, Integer userId, Integer categoryId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		Category cat = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Ctaegory", "CategoryId", categoryId));
		Post post = this.modelMapper.map(pdto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);	
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO pdto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(pdto.getTitle());
		post.setContent(pdto.getContent());
		post.setImageName(pdto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

//		int pageSize = 5;
//		int pageNumber = 1;

		Sort sort = null;

		if (sortDirection.equalsIgnoreCase("asc")) {
			System.out.println("\nDirection\n"+sortDirection);
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePosts = this.postRepo.findAll(p);
		List<Post> allPosts = pagePosts.getContent();
		List<PostDTO> pdto = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(pdto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElement(pagePosts.getTotalElements());
		;
		postResponse.setTotalPages(pagePosts.getTotalPages());

		postResponse.setLastPage(pagePosts.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostByCategoryId(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
		List<Post> post = this.postRepo.findByCategory(cat);
		List<PostDTO> posts = post.stream().map((postdata) -> this.modelMapper.map(postdata, PostDTO.class))
				.collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> post = this.postRepo.findByUser(user);
		List<PostDTO> posts = post.stream().map((postData) -> this.modelMapper.map(postData, PostDTO.class))
				.collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDTO> p= posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return p;
	}

}
