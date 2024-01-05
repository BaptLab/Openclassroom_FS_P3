package com.example.openclassroom_P3_chatop.dto;

import java.util.List;

import com.example.openclassroom_P3_chatop.model.Rental;

public class RentalsResponseDTO {
    private List<Rental> rentals;

    public RentalsResponseDTO(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
