package com.lcncbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcncbe.model.User;

public interface UserRepository extends JpaRepository< User ,Long> {
	public User findByEmail(String email);
	public User findByActivationToken(String token);
}