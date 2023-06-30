package com.chatop.backend.controller;

import com.chatop.backend.model.DAORental;
import com.chatop.backend.model.RentalDTO;
import com.chatop.backend.service.RentalService;
import com.chatop.backend.service.StorageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    @Autowired
    private final RentalService rentalService;

    @Autowired
    private final StorageService storageService;

    @Autowired
    public RentalController(RentalService rentalService, StorageService storageService) {

        this.rentalService = rentalService;
        this.storageService = storageService;
    }

    @GetMapping
    @ApiOperation(value = "Récupère toutes les locations")
    public ResponseEntity<RentalResponse> getAllRentals() {
        List<DAORental> rentals = rentalService.findAll();

        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        RentalResponse response = new RentalResponse();
        response.setRentals(rentalDTOs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Récupère une location particulière")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable(value = "id") Long rentalId) {
        DAORental daoRental = rentalService.getRentalById(rentalId);
        if (daoRental == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        RentalDTO rentalDTO = convertToDto(daoRental);  // Convert Rental entity to DTO
        return ResponseEntity.ok(rentalDTO);
    }

    private RentalDTO convertToDto(DAORental daoRental) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(daoRental.getId());
        rentalDTO.setName(daoRental.getName());
        rentalDTO.setSurface(daoRental.getSurface());
        rentalDTO.setPrice(daoRental.getPrice());
        rentalDTO.setPicturePath(daoRental.getPicturePath());
        rentalDTO.setDescription(daoRental.getDescription());
        rentalDTO.setCreated_at(daoRental.getCreatedAt());
        rentalDTO.setUpdated_at(daoRental.getUpdatedAt());
        rentalDTO.setOwner_id(daoRental.getOwner().getId()); // get the ID of the owner

        return rentalDTO;
    }


    @PostMapping
    @ApiOperation(value = "Enregistre une nouvelle location")
    public ResponseEntity<?> createRental(@RequestPart("picture") MultipartFile picture, @ModelAttribute RentalDTO rentalDTO) throws IOException {
        // enregistre l'image dans le service de stockage storageService et renvoie le chemin d'accès de l'image enregistré.
        String picturePath = storageService.store(picture);
        try {
            // le chemin d'accès de l'image est défini dans l'objet rentalDTO
            rentalDTO.setPicturePath(picturePath);

            // On peut maintenant passer le DTO au service qui gère les opérations de location
             rentalService.createRental(rentalDTO);
            return ResponseEntity.ok("{\"message\":\"Rental created !\"}");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid rental data!");

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Modifie une location particulière")
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestPart(value = "picture", required = false) MultipartFile picture, @ModelAttribute RentalDTO rentalDTO) throws IOException {
        DAORental existingRental = rentalService.getRentalById(id);
        if (existingRental == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // prend en charge l'enregistrement de l'image (idem que dans createRental)
        if (picture != null && !picture.isEmpty()) {
            String picturePath = storageService.store(picture);
            rentalDTO.setPicturePath(picturePath);
        }

        // Met à jour le rental à partir de la DTO
        try{
            rentalService.updateRental(existingRental,rentalDTO);
            return ResponseEntity.ok("{\"message\":\"Rental updated !\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid rental data!");

    }
}
