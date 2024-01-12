package com.example.openclassroom_P3_chatop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.openclassroom_P3_chatop.dto.MessageDTO;
import com.example.openclassroom_P3_chatop.dto.MessageResponseDTO;
import com.example.openclassroom_P3_chatop.model.Message;
import com.example.openclassroom_P3_chatop.services.MessageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PostMapping("/messages")
	public ResponseEntity<MessageResponseDTO> saveMessage(@RequestBody MessageDTO messageDTO,
			HttpServletRequest request) throws Exception {
		Message message = messageService.saveMessage(messageDTO);
		if (message != null) {
			MessageResponseDTO messageResponse = new MessageResponseDTO("Message send with success");
			;
			return ResponseEntity.ok(messageResponse);
		} else {
			MessageResponseDTO messageResponse = new MessageResponseDTO("Error sending the message");
			return ResponseEntity.badRequest().body(messageResponse);
		}
	}
}
