package com.chatop.backend.controller;

import com.chatop.backend.model.RentalDTO;
import lombok.Data;

import java.util.List;

@Data
public class RentalResponse {
    List<RentalDTO> rentals;

}
