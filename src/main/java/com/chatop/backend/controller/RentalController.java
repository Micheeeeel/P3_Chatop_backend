package com.chatop.backend.controller;


import com.chatop.backend.model.DAORental;
import com.chatop.backend.model.DAOUser;
import com.chatop.backend.model.RentalDTO;
import com.chatop.backend.model.UserDTO;
import com.chatop.backend.service.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<DAORental>> getAllRentals() {
        List<DAORental> rentals = rentalService.findAll();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
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

        UserDTO owner = convertUserToDto(daoRental.getOwner());
        rentalDTO.setOwner(owner);
        return rentalDTO;
    }

    private UserDTO convertUserToDto(DAOUser daoUser) {
        UserDTO userDTO = new UserDTO(daoUser.getId(),daoUser.getName(), daoUser.getEmail(), daoUser.getCreatedAt(), daoUser.getUpdatedAt());
        return userDTO;
    }

  /*  @PostMapping
    public ResponseEntity<?> createRental(@RequestBody RentalDTO rental) {
        rentalService.createRental(rental);
        return ResponseEntity.ok().body("Rental created !");
    }*/
    @PostMapping
    public ResponseEntity<?> createRental(@RequestParam("rental") String rentalJson, @RequestParam("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RentalDTO rentalDTO = objectMapper.readValue(rentalJson, RentalDTO.class);

            // store file and get file path
            if (file != null && !file.isEmpty()) {
                // Let's store the files into a folder "upload" in folder ressources
                String uploadDirectory = System.getProperty("user.dir") + "/backendImageUpload";

                // create folder if not exists
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                // Sauvegardez le fichier et obtenez le chemin d'accès
                String fileName = file.getOriginalFilename();
                String filePath = Paths.get(uploadDirectory, fileName).toString();
                File dest = new File(filePath);
                file.transferTo(dest);

                // Stockez le chemin d'accès dans le DTO
                rentalDTO.setPicturePath(filePath);
            }

            rentalService.createRental(rentalDTO);

            return ResponseEntity.ok().body("Rental created !");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid rental data!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable(value = "id") Long rentalId, @Valid @RequestBody RentalDTO rentalDetails) {
        RentalDTO updatedRental = rentalService.updateRental(rentalId, rentalDetails);
        return ResponseEntity.ok(updatedRental);
    }
}
