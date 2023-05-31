package com.chatop.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data  // @Data is a Lombok annotation. No need to add the getters and setters.
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user") // indique le nom de la table associée.
public class DAOUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @JsonIgnore  // because we don't want to serialize the password
    private String password;

 /*   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/
}
