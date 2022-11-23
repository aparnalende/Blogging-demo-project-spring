package com.example.blogproject.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blogproject.payloads.UserDTO;

//@Service
public interface UserService {

	UserDTO registerNewUser(UserDTO user);

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(UserDTO user, Integer userId);

	UserDTO getUserById(Integer userId);

	List<UserDTO> getAllUsers();

	void deleteUser(Integer userId);
}
