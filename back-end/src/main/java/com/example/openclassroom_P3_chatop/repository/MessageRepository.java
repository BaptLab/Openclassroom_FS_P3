package com.example.openclassroom_P3_chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.openclassroom_P3_chatop.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

}
