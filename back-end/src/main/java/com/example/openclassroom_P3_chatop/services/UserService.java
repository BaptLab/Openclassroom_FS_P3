package com.example.openclassroom_P3_chatop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.openclassroom_P3_chatop.dto.LoginDTO;
import com.example.openclassroom_P3_chatop.dto.UserDTO;
import com.example.openclassroom_P3_chatop.model.User;
import com.example.openclassroom_P3_chatop.repository.UserRepository;
import com.example.openclassroom_P3_chatop.utils.JWTUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationService authentictionService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User saveUser(UserDTO userDTO) {
		userDTO.setPasswordEncoder(passwordEncoder);
		User savedUser = userRepository.save(UserDTO.toEntity(userDTO, passwordEncoder));
		return savedUser;
	}

	public String registerUser(UserDTO user) {
		User savedUser = saveUser(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		String token = JWTUtils.generateToken(authentication);
		return token;
	}

	public Optional<User> getUserById(final Long id) {
		return userRepository.findById(id);
	}

	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public void deleteUserById(final Long id) {
		userRepository.deleteById(id);
	}

	public Optional<User> getUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public Boolean isUserPresent(LoginDTO loginDTO) {
		// Check for user in DB based on email
		if (getUserByEmail(loginDTO.getEmail()).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public Optional<User> getCurrentUser(Authentication authentication) throws Exception {
		String userMail = JWTUtils.getUserMailFromAuthentication(authentication);
		if (userMail != null) {
			Optional<User> optionalUser = getUserByEmail(userMail);
			if (optionalUser.isPresent()) {
				return optionalUser;
			} else {
				throw new Exception("error");
			}
		}
		;
		return null;
	}

	public String loginUser(LoginDTO loginDTO) {
		Optional<User> userOptional = getUserByEmail(loginDTO.getEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (JWTUtils.isPwdMatching(loginDTO, user)) {
				Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
						user.getPassword());
				String token = JWTUtils.generateToken(authentication);
				return token;
			} else {
				throw new UnauthorizedException("Incorrect credentials");
			}
		} else {
			throw new UnauthorizedException("User not found");
		}
	}

}
