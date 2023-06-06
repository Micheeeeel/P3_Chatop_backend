package com.chatop.backend.repository;

import com.chatop.backend.model.DAORental;
import com.chatop.backend.model.RentalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<DAORental, Long> {
}