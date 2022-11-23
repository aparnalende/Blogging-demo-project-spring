package com.example.blogproject.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogproject.entities.Category;
import com.example.blogproject.exceptions.ResourceNotFoundException;
import com.example.blogproject.payloads.CategoryDTO;
import com.example.blogproject.repository.CategoryRepo;
import com.example.blogproject.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper ModelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		Category ctr = this.ModelMapper.map(categoryDto, Category.class);
		Category cat = this.categoryRepo.save(ctr);
		return this.ModelMapper.map(cat, CategoryDTO.class);

	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category ctr = this.categoryRepo.save(cat);
		return this.ModelMapper.map(ctr, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Categoty Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDTO getSingleCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.ModelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategory() {
		List<Category> catList = this.categoryRepo.findAll();
		List<CategoryDTO> catDto = catList.stream().map((category) -> this.ModelMapper.map(catList, CategoryDTO.class))
				.collect(Collectors.toList());
		return catDto;
	}

}
