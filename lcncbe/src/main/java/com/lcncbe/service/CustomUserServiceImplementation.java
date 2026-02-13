package com.lcncbe.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lcncbe.model.User;
import com.lcncbe.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;

@Service
public class CustomUserServiceImplementation implements CustomUserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public CustomUserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		User isEmailExist=userRepository.findByEmail(email);
		return isEmailExist;
	}

	@Override
	public User createUser(String email, String password, String firstNString, String lastNString,
			String activationToken, Calendar calendar) {
		// TODO Auto-generated method stub
		
		User createdUser=new User();
		
		createdUser.setDisplayName(firstNString+' '+ lastNString);
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstNString);
		createdUser.setLastName(lastNString);
		createdUser.setEnabled(false);
		createdUser.setActivationToken(activationToken);
		createdUser.setTokenExpiryDate(calendar.getTime());
		
		User savedUser=userRepository.save(createdUser);
		return savedUser;
	}

	@Override
	public User findByActivationToken(String token) {
		// TODO Auto-generated method stub
		User user=userRepository.findByActivationToken(token);
		return user;
	}

	@Override
	public Boolean activateUser(String email) {
		// TODO Auto-generated method stub
		User user=userRepository.findByEmail(email);
		user.setEnabled(true);
	    user.setActivationToken(null); 
	    user.setTokenExpiryDate(null); // Clear the expiry date
	    userRepository.save(user);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
	    if(user == null) {
	        throw new UsernameNotFoundException("User not found with email - " + username);
	    }
	    System.out.println(user);
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    UserDetails userDetails = new org.springframework.security.core.userdetails.User(
	        user.getEmail(),
	        user.getPassword(),
	        user.isEnabled(), 
	        true, 
	        true, 
	        true, 
	        authorities
	    );
	    return userDetails;
	}

}
