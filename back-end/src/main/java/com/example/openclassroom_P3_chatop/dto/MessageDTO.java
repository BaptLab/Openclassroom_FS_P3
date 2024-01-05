package com.example.openclassroom_P3_chatop.dto;

import com.example.openclassroom_P3_chatop.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {
	@JsonProperty("rental_id")
    private Long rentalId;

    @JsonProperty("user_id")
    private Long userId;

	private String message;

	public MessageDTO(Long rentalId, Long userId, String message) {
		this.rentalId = rentalId;
		this.userId = userId;
		this.message = message;
	}

	public Message toEntity() {
		Message message = new Message(this.rentalId, this.userId, this.message);
		return message;
	}


	public Long getRentalId() {
		return rentalId;
	}

	public void setRentalId(Long rentalId) {
		this.rentalId = rentalId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
