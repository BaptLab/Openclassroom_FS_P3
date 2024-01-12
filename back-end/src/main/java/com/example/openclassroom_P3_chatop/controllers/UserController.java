package com.example.openclassroom_P3_chatop.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.openclassroom_P3_chatop.model.User;

import com.example.openclassroom_P3_chatop.dto.LoginDTO;
import com.example.openclassroom_P3_chatop.dto.TokenResponseDTO;
import com.example.openclassroom_P3_chatop.dto.UserDTO;
import com.example.openclassroom_P3_chatop.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping("/me")
	public ResponseEntity<Optional<User>> getCurrentUser() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> currentUser = userService.getCurrentUser(authentication);
		return new ResponseEntity<>(currentUser, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<TokenResponseDTO> registerUser(@RequestBody UserDTO submittedUser) {
		String generatedToken = userService.registerUser(submittedUser);
	    TokenResponseDTO tokenResponse = new TokenResponseDTO(generatedToken);
		return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
	    String token = userService.loginUser(loginDTO);
	    TokenResponseDTO tokenResponse = new TokenResponseDTO(token);
	    System.out.print(token);
	    return ResponseEntity.ok(tokenResponse);
	}
}
