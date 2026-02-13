package com.lcncbe.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

	String generateToken(Authentication authentication);
	String getEmailFromToken(String jwt) ;

}
