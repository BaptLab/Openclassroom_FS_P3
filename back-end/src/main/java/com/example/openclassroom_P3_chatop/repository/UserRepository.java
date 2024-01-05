package com.example.openclassroom_P3_chatop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.openclassroom_P3_chatop.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public Optional<User> findByEmail(String email);
}