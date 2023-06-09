package com.chatop.backend.controller;


import com.chatop.backend.model.DAORental;
import com.chatop.backend.model.RentalDTO;
import com.chatop.backend.service.RentalService;
import com.chatop.backend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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


  /*  @PostMapping
    public ResponseEntity<?> createRental(@RequestBody RentalDTO rental) {
        rentalService.createRental(rental);
        return ResponseEntity.ok().body("Rental created !");
    }*/
    @PostMapping
    public ResponseEntity<?> createRental(@RequestPart("picture") MultipartFile picture, @ModelAttribute RentalDTO rentalDTO) throws IOException {
        String picturePath = storageService.store(picture);
        try {
            rentalDTO.setPicturePath(picturePath);

            // Vous pouvez maintenant passer votre DTO au service qui gère les opérations de location
             rentalService.createRental(rentalDTO);
            return ResponseEntity.ok("{\"message\":\"Rental created !\"}");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid rental data!");

    }

       /* ObjectMapper objectMapper = new ObjectMapper();
        try {
            RentalDTO rentalDTO = objectMapper.readValue(rentalStr, RentalDTO.class);

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
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable(value = "id") Long rentalId, @Valid @RequestBody RentalDTO rentalDetails) {
        RentalDTO updatedRental = rentalService.updateRental(rentalId, rentalDetails);
        return ResponseEntity.ok(updatedRental);
    }
}
