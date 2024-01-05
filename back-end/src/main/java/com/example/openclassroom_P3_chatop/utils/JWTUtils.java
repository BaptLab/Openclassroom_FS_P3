package com.example.openclassroom_P3_chatop.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.openclassroom_P3_chatop.dto.LoginDTO;
import com.example.openclassroom_P3_chatop.model.User;
import com.example.openclassroom_P3_chatop.repository.UserRepository;
import com.example.openclassroom_P3_chatop.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTUtils {

	public static JwtEncoder jwtEncoder;
	public static JwtDecoder jwtDecoder;
	public static PasswordEncoder passwordEncoder;
	public static UserRepository userRepository;

	public JWTUtils(PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, UserService userService, JwtDecoder jwtDecoder, UserRepository userRepository) {
	    JWTUtils.passwordEncoder = passwordEncoder;
	    JWTUtils.jwtEncoder = jwtEncoder;
	    JWTUtils.jwtDecoder = jwtDecoder;
	    JWTUtils.userRepository = userRepository;
	}

    public static String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName()).build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    public static String extractToken(HttpServletRequest request) {
		 String authorizationHeader = request.getHeader("Authorization");
		 if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			 String token = authorizationHeader.substring(7);
			 return token;
		 }
		 return null;
	 }

    public static boolean isTokenValid(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming authentication is based on JwtAuthenticationToken
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
                Jwt jwt = jwtAuthenticationToken.getToken();
                if(isTokenNotExpired(jwt)) {
                	return true;
                }
                else {
                	return false;
                }
            }
        }
        return false;
    }

    private static boolean isTokenNotExpired(Jwt jwt) {
        Instant expirationTime = jwt.getExpiresAt();
        Instant now = Instant.now();
        return expirationTime != null && !now.isAfter(expirationTime);
    }

    public static String getUserMailFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
                Jwt jwt = jwtAuthenticationToken.getToken();
                String userEmail = jwt.getClaim("sub").toString(); // Use "sub" claim for email
                try {
                    return userEmail;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "User not found !";
    	}

    public static boolean isPwdMatching(LoginDTO loginDTO, User user) {
    	System.out.print(loginDTO.getPassword());
    	System.out.print(user.getPassword());
    	return passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
   	}

    public static Authentication getAuthentication(String token) {
        if (token != null) {
            Jwt jwt = jwtDecoder().decode(token);
            if (isTokenNotExpired(jwt)) {
                return new JwtAuthenticationToken(
                		jwt,
                        new ArrayList<>()
                );
            }
        }
        return null;
    }

    public static JwtDecoder jwtDecoder() {
        return jwtDecoder;
    }

	public static String pwdEncoder(String password) {
		password = passwordEncoder.encode(password);
		return password;
	}

	 public static Optional<User> getUserByEmail(String email, UserRepository userRepository) {
		 return userRepository.findByEmail(email);
	 }

}


