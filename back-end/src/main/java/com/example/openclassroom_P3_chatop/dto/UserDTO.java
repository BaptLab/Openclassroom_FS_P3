package com.example.openclassroom_P3_chatop.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.openclassroom_P3_chatop.model.User;
import com.example.openclassroom_P3_chatop.utils.JWTUtils;

public class UserDTO {

	private PasswordEncoder passwordEncoder;

	private Long id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public UserDTO(Long id, String name, String email, String password, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = setPassword(password);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static UserDTO fromEntity(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCreationDate(),
				user.getUpdateDate());
	}

	public static User toEntity(UserDTO userDTO, PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setId(userDTO.id);
		user.setEmail(userDTO.email);
		user.setName(userDTO.name);
		user.setPassword(userDTO.password);
		user.setCreationDate(setCreatedAt(userDTO.createdAt)); // Fix this line
		user.setUpdateDate(setUpdatedAt());
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String setPassword(String password) {
		return JWTUtils.pwdEncoder(password);
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public static LocalDateTime setCreatedAt(LocalDateTime createdAt) {
		if (createdAt == null) {
			return LocalDateTime.now();
		} else {
			return createdAt;
		}
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public static LocalDateTime setUpdatedAt() {
		return LocalDateTime.now();
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static UserDTO fromEntity(Optional<User> user) {
		// TODO Auto-generated method stub
		return null;
	}

}
