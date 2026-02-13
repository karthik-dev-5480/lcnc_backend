package com.lcncbe.service;

import java.util.Calendar;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lcncbe.model.User;


public interface CustomUserService {

	User findByEmail(String email);

	User createUser(String email, String password, String firstNString, String lastNString, String activationToken,
			Calendar calendar);

	User findByActivationToken(String token);

	Boolean activateUser(String email);

	UserDetails loadUserByUsername(String username);	

}
