package com.chatop.backend.repository;

import com.chatop.backend.model.DAOMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<DAOMessage, Long> {
}
