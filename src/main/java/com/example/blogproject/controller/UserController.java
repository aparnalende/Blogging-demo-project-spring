package com.example.blogproject.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogproject.payloads.ApiResponse;
import com.example.blogproject.payloads.UserDTO;
import com.example.blogproject.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	// post create user

	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
		UserDTO createDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDTO>(createDto, HttpStatus.CREATED);
	}
	// put- update user

	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDto, @PathVariable Integer userId) {
		UserDTO updatedUser = this.userService.updateUser(userDto, userId);

		return ResponseEntity.ok(updatedUser);
	}
	// delete user

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {

		this.userService.deleteUser(userId);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully!", true), HttpStatus.OK);
	}

	// get
	@GetMapping("/getAll")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	// get by id
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDTO> getSIngleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
