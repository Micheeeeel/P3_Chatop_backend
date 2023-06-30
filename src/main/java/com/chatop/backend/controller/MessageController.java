package com.chatop.backend.controller;

import com.chatop.backend.model.DAOMessage;
import com.chatop.backend.model.MessageDTO;
import com.chatop.backend.service.MessageService;
import com.chatop.backend.service.RentalService;
import com.chatop.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.backend.model.DAOUser;
import com.chatop.backend.model.DAORental;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final RentalService rentalService;
    private final UserService userService;

    public MessageController(UserService userService, RentalService rentalService, MessageService messageService) {
        this.userService = userService;
        this.rentalService = rentalService;
        this.messageService = messageService;

    }

    @PostMapping
    @ApiOperation(value = "Enregistre un nouveau message")
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {

        DAOMessage message = new DAOMessage();
        // fetch the user and rental based on the ids from messageDTO
        DAOUser user = userService.getUserById(messageDTO.getUser_id());
        DAORental rental = rentalService.getRentalById(messageDTO.getRental_id());

        // set the user and rental to the message
        message.setUser(user);
        message.setRental(rental);
        message.setMessage(messageDTO.getMessage());

        // insert new message into db
        messageService.createMessage(message);

        return ResponseEntity.ok("{\"message\":\"Message created !\"}");
    }

}

