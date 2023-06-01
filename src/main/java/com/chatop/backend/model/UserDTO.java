package com.chatop.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class UserDTO {
    private Integer id;
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date created_at;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date updated_at;

    public UserDTO(Integer id, String name, String email, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void SetEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }



}