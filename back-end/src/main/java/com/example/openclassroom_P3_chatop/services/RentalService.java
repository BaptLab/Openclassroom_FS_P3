package com.example.openclassroom_P3_chatop.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.openclassroom_P3_chatop.dto.RentalDTO;
import com.example.openclassroom_P3_chatop.model.Rental;
import com.example.openclassroom_P3_chatop.model.User;
import com.example.openclassroom_P3_chatop.repository.RentalRepository;
import com.example.openclassroom_P3_chatop.repository.UserRepository;
import com.example.openclassroom_P3_chatop.utils.JWTUtils;

@Service
public class RentalService {

	@Autowired
	public RentalRepository rentalRepository;
	
	@Autowired 
	public UserRepository userRepository;
	
	public Optional<Rental> getRentalById(final Long id){
		return rentalRepository.findById(id);
	}
	
	public List<Rental> getAllRentals(){
		return (List<Rental>) rentalRepository.findAll();
		}
	
	public void deleteRentalById(final Long id) {
		rentalRepository.deleteById(id);
	}
	
	
	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);
	}
	

	 private static String uploadDir = "src/main/resources/static/images";
	

	 public static String saveFile(MultipartFile file) throws IOException {
		    if (uploadDir == null) {
		    	System.out.printf("Value of uploadDir = %s%n", uploadDir);
		        throw new IllegalStateException("Upload directory is not configured.");
		    }
		    // Create a unique filename using UUID
		    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		    // Define the path where the file will be stored
		    Path filePath = Path.of(uploadDir).resolve(fileName).toAbsolutePath();
		    // Ensure the upload directory exists, create if not
		    Files.createDirectories(filePath.getParent());
		    // Copy the file to the target location
		    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		    // Return the relative path as a string
		    return "/images/" + fileName;
		}
	 
	 public Rental updateRentalFromDTO(Rental rental, RentalDTO updatedRentalDTO, User ownerUser) {
	        if (updatedRentalDTO.getName() != null) {
	            rental.setName(updatedRentalDTO.getName());
	        }
	        if (updatedRentalDTO.getSurface() != null) {
	            rental.setSurface(updatedRentalDTO.getSurface());
	        }
	        if (updatedRentalDTO.getPrice() != null) {
	            rental.setPrice(updatedRentalDTO.getPrice());
	        }
	        if (updatedRentalDTO.getPicture() != null) {
	            rental.setPictureUrl(transformPictureToPath(updatedRentalDTO.getPicture()));
	        }
	        if (updatedRentalDTO.getDescription() != null) {
	            rental.setDescription(updatedRentalDTO.getDescription());
	        }
	        rental.setUpdateDate(LocalDateTime.now());
	        return rental;
	    }

	 public String transformPictureToPath(MultipartFile picturePath) {
	        try {
				return RentalService.saveFile(picturePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }
	 
	 public Rental updateRentalById(RentalDTO rentalDTO, Long id, MultipartFile picture) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	String userMail = JWTUtils.getUserMailFromAuthentication(authentication);
	        Optional<User> optionalUser = JWTUtils.getUserByEmail(userMail, userRepository);
	        if (optionalUser.isPresent()) {
	            Optional<Rental> optionalExistingRental = getRentalById(id);
	            if (optionalExistingRental.isPresent()) {
	                User ownerUser = optionalUser.get();
	                Rental existingRental = optionalExistingRental.get();
	                Rental updatedRental = updateRentalFromDTO(existingRental, rentalDTO, ownerUser);
	                return updatedRental;
	            }
	        }
			return null;
	 }

	public Rental createRental(RentalDTO rentalDTO, MultipartFile picture) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	String userMail = JWTUtils.getUserMailFromAuthentication(authentication);
            Optional<User> optionalUser = JWTUtils.getUserByEmail(userMail, userRepository);
            if (optionalUser.isPresent()) {
                User ownerUser = optionalUser.get();
                RentalDTO createdRentalDTO = new RentalDTO(
                        null, // id
                        rentalDTO.getName(),
                        rentalDTO.getSurface(),
                        rentalDTO.getPrice(),
                        picture,
                        rentalDTO.getDescription(),
                        ownerUser.getId(), // Remove owner_id from here
                        null, // creationDate
                        null  // updateDate
                );
                Rental rental = createdRentalDTO.toEntity(); // Pass the authenticated user to the DTO
                Rental createdRental = saveRental(rental);
                return createdRental;
            }
        }
        return null;
    }

}
