package com.chatop.backend.service;

import com.chatop.backend.exceptions.RessourceNotFoundException;
import com.chatop.backend.model.DAORental;
import com.chatop.backend.model.RentalDTO;
import com.chatop.backend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<DAORental> findAll() {
        return rentalRepository.findAll();
    }


    public DAORental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("Rental not found with id: " + id));
    }

    public void createRental(RentalDTO rentalDTO) {
        DAORental newRental = new DAORental();
        // Copy fields from rentalDTO to newRental
        newRental.setName(rentalDTO.getName());
        newRental.setSurface(rentalDTO.getSurface());
        newRental.setPrice(rentalDTO.getPrice());
        newRental.setPicture(rentalDTO.getPicture());
        newRental.setDescription(rentalDTO.getDescription());

        rentalRepository.save(newRental);
    }

    public RentalDTO updateRental(Long rentalId, RentalDTO rentalDetails) {
        DAORental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RessourceNotFoundException("Rental not found with id: " + rentalId));

        // Here set the properties of the rental object using rentalDetails
        rental.setName(rentalDetails.getName());
        rental.setSurface(rentalDetails.getSurface());
        rental.setPrice(rentalDetails.getPrice());
        rental.setPicture(rentalDetails.getPicture());
        rental.setDescription(rentalDetails.getDescription());

        // Save the updated rental object
        rentalRepository.save(rental);

        return rentalDetails;
    }
}
