package com.chatop.backend.controller;

import com.chatop.backend.model.DAOUser;
import com.chatop.backend.model.UserDTO;

import com.chatop.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Récupère un utilisteur particulier")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id) {
        Optional<DAOUser> user = userService.findById(id);
        if (user.isPresent()) {
            UserDTO userDto = convertToDto(user.get());
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    private UserDTO convertToDto(DAOUser daoUser) {
        UserDTO userDTO = new UserDTO(
                daoUser.getId(),
                daoUser.getName(),
                daoUser.getEmail(),
                daoUser.getCreatedAt(),
                daoUser.getUpdatedAt());
        return userDTO;
    }
}
