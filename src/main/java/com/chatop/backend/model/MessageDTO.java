package com.chatop.backend.model;

import lombok.Data;

@Data

public class MessageDTO {
    private String message;
    private Integer user_id;
    private Long rental_id;

    // getters et setters
}
