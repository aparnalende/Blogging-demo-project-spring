package com.example.blogproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogproject.payloads.ApiResponse;
import com.example.blogproject.payloads.CategoryDTO;
import com.example.blogproject.payloads.UserDTO;
import com.example.blogproject.repository.CategoryRepo;
import com.example.blogproject.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	CategoryRepo catRepo;

	@Autowired
	CategoryService catService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		CategoryDTO ctr = this.catService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDTO>(ctr, HttpStatus.CREATED);
	}
	// update

	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto,
			@PathVariable Integer catId) {
		CategoryDTO ctr = this.catService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDTO>(ctr, HttpStatus.OK);
	}

	// delete

	@DeleteMapping("/delete/{catId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer catId) {

		this.catService.deleteCategory(catId);
		return new ResponseEntity(new ApiResponse("Category Deleted Successfully!", true), HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDTO>> getAll() {
		return ResponseEntity.ok(this.catService.getCategory());
	}

	// get by id
	@GetMapping("/get/{catId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {
		return ResponseEntity.ok(this.catService.getSingleCategory(catId));
	}
}
