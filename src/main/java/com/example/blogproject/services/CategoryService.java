package com.example.blogproject.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blogproject.payloads.CategoryDTO;

//@Service
public interface CategoryService {

	// create
	CategoryDTO createCategory(CategoryDTO categoryDto);

	// update
	CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId);
	// delete

	void deleteCategory(Integer categoryId);

	// getAll
	CategoryDTO getSingleCategory(Integer categoryId);

	List<CategoryDTO> getCategory();

}
