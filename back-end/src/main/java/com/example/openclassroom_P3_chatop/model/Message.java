package com.example.openclassroom_P3_chatop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_id")
    private Long rentalId;

    @Column(name = "user_id")
    private Long userId;

	private String message;

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    public Message(Long rentalId, Long ownerId, String message) {
        this.rentalId = rentalId;
        this.userId = ownerId;
        this.message = message;
        this.creationDate = (creationDate == null) ? LocalDateTime.now() : creationDate;
        this.updateDate = setUpdateDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = (creationDate == null) ? LocalDateTime.now() : creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    private LocalDateTime setUpdateDate() {
        return LocalDateTime.now();
    }
}
