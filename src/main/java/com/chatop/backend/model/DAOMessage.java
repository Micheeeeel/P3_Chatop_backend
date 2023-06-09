package com.chatop.backend.model;

import lombok.Data;

import javax.persistence.*;

@Data  // @Data is a Lombok annotation. No need to add the getters and setters.
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "messages")
public class DAOMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private DAOUser user;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private DAORental rental;

    @Column(nullable = false)
    private String message;

}
