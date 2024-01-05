package com.example.openclassroom_P3_chatop.services;

import org.springframework.stereotype.Service;

import com.example.openclassroom_P3_chatop.dto.MessageDTO;
import com.example.openclassroom_P3_chatop.model.Message;
import com.example.openclassroom_P3_chatop.repository.MessageRepository;

@Service
public class MessageService {

	private final MessageRepository messageRepository;

	public MessageService(MessageRepository messageRepository, AuthenticationService authenticationService) {
		this.messageRepository = messageRepository;
	}

	public Message saveMessage(MessageDTO messageDTO) throws Exception {
		Message message = messageDTO.toEntity();
		Message savedMessage = messageRepository.save(message);
		return savedMessage;
	}
}
