package com.breudes.gastrohub.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 14, nullable = false)
    private String document;

    private Boolean active;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user")
    private User user;

    public Owner() {
    }

    public Owner(Long id, String document, Boolean active, List<Restaurant> restaurants, User user) {
        this.id = id;
        this.document = document;
        this.active = active;
        this.restaurants = restaurants;
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

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", active=" + active +
                ", restaurants=" + restaurants +
                ", user=" + user +
                '}';
    }
}
