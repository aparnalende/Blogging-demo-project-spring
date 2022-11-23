package com.example.blogproject.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogproject.config.AppConstants;
import com.example.blogproject.entities.Role;
import com.example.blogproject.entities.User;
import com.example.blogproject.exceptions.ResourceNotFoundException;
import com.example.blogproject.payloads.UserDTO;
import com.example.blogproject.repository.RoleRepository;
import com.example.blogproject.repository.UserRepo;
import com.example.blogproject.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = this.dtoToUser(userDto);
		User saveUser = this.userRepo.save(user);
		return this.userToDto(saveUser);
	}

	private User dtoToUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDTO userToDto(User user) {
		UserDTO userDto = this.modelMapper.map(user, UserDTO.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDTO userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		List<UserDTO> userDto = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		this.userRepo.delete(user);
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		User userinfo = this.modelMapper.map(userDTO, User.class);
		// encoded the password
		userinfo.setPassword(this.passwordEncoder.encode(userinfo.getPassword()));

		// roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();

		userinfo.getRole().add(role);
		User newUser = this.userRepo.save(userinfo);

		return this.modelMapper.map(newUser, UserDTO.class);

	}
}
