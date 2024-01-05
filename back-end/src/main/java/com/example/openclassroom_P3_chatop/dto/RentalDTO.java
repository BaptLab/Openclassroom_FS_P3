package com.example.openclassroom_P3_chatop.dto;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.openclassroom_P3_chatop.model.Rental;
import com.example.openclassroom_P3_chatop.services.RentalService;

public class RentalDTO {

    private Long id;
    private String name;
    private Long surface;
    private Long price;
    private MultipartFile picture;
    private String description;
    private Long ownerId;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public RentalDTO(Long id, String name, Long surface, Long price, MultipartFile picture, String description, Long ownerId, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.ownerId = ownerId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Rental toEntity() {
        Rental rental = new Rental();
        rental.setId(this.id);
        rental.setName(this.name);
        rental.setSurface(this.surface);
        rental.setPrice(this.price);
        rental.setPictureUrl(transformPictureToPath(this.picture));
        rental.setOwnerId(this.ownerId);
        rental.setCreationDate(setCreatedAt(this.creationDate));
        rental.setUpdateDate(setUpdateDate());
        rental.setDescription(description);

        return rental;
    }


    public void setPicture(MultipartFile picture) {
        this.picture = picture;
      
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSurface() {
        return surface;
    }

    public void setSurface(Long surface) {
        this.surface = surface;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public String transformPictureToPath(MultipartFile picturePath) {
        try {
			return RentalService.saveFile(picturePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public static LocalDateTime setCreatedAt(LocalDateTime createdAt) {
    	if(createdAt == null)
    	{   
    		return LocalDateTime.now(); 		
    	}
    	else {
    		return createdAt;
    	}	
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public LocalDateTime setUpdateDate() {
    	 return LocalDateTime.now();
    }
}
