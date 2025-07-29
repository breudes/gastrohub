package com.breudes.gastrohub.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 11, nullable = false)
    private String document;

    private Boolean active;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user")
    private User user;

    public Client() {
    }

    public Client(Long id, String document, Boolean active, User user) {
        this.id = id;
        this.document = document;
        this.active = active;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", active=" + active +
                ", user=" + user +
                '}';
    }
}
