package com.example.openclassroom_P3_chatop.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.openclassroom_P3_chatop.utils.JWTUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Service
public class AuthenticationService {

	private static PasswordEncoder passwordEncoder;

	public AuthenticationService(PasswordEncoder passwordEncoder) {
		AuthenticationService.setPasswordEncoder(passwordEncoder);
	}

	public Authentication temporaryGetAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Boolean temporaryGetTokenValidation(Authentication authentication) {
		if (JWTUtils.isTokenValid(authentication)) {
			return true;
		} else {
			return false;
		}
	}

	public static PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public static void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		AuthenticationService.passwordEncoder = passwordEncoder;
	}

}
