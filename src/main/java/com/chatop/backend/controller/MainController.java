package com.chatop.backend.controller;

import com.chatop.backend.model.UserDTO;
import com.chatop.backend.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.chatop.backend.model.DAOUser;
import com.chatop.backend.service.UserService;

import io.swagger.annotations.ApiResponses;

@Api("API pour les opérations CRUD sur les utilisateurs.")
@RestController // This means that this class is a RestController bean, and it returns a JSON format into the HTTP request body
@RequestMapping(path="/api")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @ApiOperation(value = "Récupère tous les utilisateurs enregistrés")
    @GetMapping(path="/auth/all")
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(ref = "#/components/schemas/DAOUser")
            )
    )
    public @ResponseBody Iterable<DAOUser> getAllUsers() {
        // This returns a JSON or XML with the users
        return userService.getUsers();
    }

    @ApiOperation(value = "Récupère les détails de l'utilisateur connecté")
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class)
            )
    )
    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        DAOUser user = userRepository.findByName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            UserDTO userResponse = new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
            return ResponseEntity.ok(userResponse);

        }
    }

}
