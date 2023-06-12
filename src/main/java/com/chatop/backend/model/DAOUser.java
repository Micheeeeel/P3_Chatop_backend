package com.chatop.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data  // @Data is a Lombok annotation. No need to add the getters and setters.
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user") // indique le nom de la table associée.
public class DAOUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    //@JsonIgnore  // because we don't want to serialize the password
    private String password;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();

        // force valuing updateAt in case it is null
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();

    }

    @OneToMany(mappedBy = "owner")
    private List<DAORental> rentals;

    @OneToMany(mappedBy = "user")
    private List<DAOMessage> messages = new ArrayList<>();

}
