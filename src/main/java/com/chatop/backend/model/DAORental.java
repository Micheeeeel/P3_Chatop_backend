package com.chatop.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data  // @Data is a Lombok annotation. No need to add the getters and setters.
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "rental")
public class DAORental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int surface;

    private int price;

    @Column(name = "picture_path")
    private String picturePath;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private DAOUser owner;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = new Date();

        // force valuing updateAt with createAt in case it is null
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @OneToMany(mappedBy = "rental")
    private List<DAOMessage> messages = new ArrayList<>();

}
