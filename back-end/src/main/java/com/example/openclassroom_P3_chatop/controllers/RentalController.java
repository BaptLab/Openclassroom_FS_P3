package com.example.openclassroom_P3_chatop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.openclassroom_P3_chatop.dto.MessageResponseDTO;
import com.example.openclassroom_P3_chatop.dto.RentalDTO;
import com.example.openclassroom_P3_chatop.dto.RentalsResponseDTO;
import com.example.openclassroom_P3_chatop.model.Rental;
import com.example.openclassroom_P3_chatop.services.RentalService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@GetMapping("/rentals")
	public ResponseEntity<RentalsResponseDTO> getAllRentals(HttpServletRequest request) {
		List<Rental> rentals = rentalService.getAllRentals();
		RentalsResponseDTO response = new RentalsResponseDTO(rentals);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/rentals/{id}")
	public ResponseEntity<Optional<Rental>> getRental(@PathVariable Long id, HttpServletRequest request) {
		Optional<Rental> rental = rentalService.getRentalById(id);
		return new ResponseEntity<>(rental, HttpStatus.OK);
	}

	@PutMapping("/rentals/{id}")
	public ResponseEntity<MessageResponseDTO> updateRental(@RequestParam("picture") MultipartFile picture,
			@ModelAttribute RentalDTO rentalDTO, @PathVariable Long id) {
		MessageResponseDTO response = new MessageResponseDTO("Rental updatedh !");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/rentals")
	public ResponseEntity<MessageResponseDTO> createRental(@RequestParam("picture") MultipartFile picture,
			@ModelAttribute RentalDTO rentalDTO) {
		Rental createdRental = rentalService.createRental(rentalDTO, picture);
		if (createdRental != null) {
			MessageResponseDTO response = new MessageResponseDTO("Rental created  !");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
