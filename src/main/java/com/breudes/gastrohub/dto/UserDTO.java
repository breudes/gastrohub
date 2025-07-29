package com.breudes.gastrohub.dto;

import com.breudes.gastrohub.model.Address;
import com.breudes.gastrohub.model.enums.UserRole;

public class UserDTO {
    private String name;
    private String preferredName;
    private String email;
    private String username;
    private String password;
    private UserRole userRole;
    private Address address;

    public UserDTO() {
    }

    public UserDTO(String name, String preferredName, String email, String username, String password, UserRole userRole, Address address) {
        this.name = name;
        this.preferredName = preferredName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", preferredName='" + preferredName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                ", address=" + address.getPostalcode() +
                '}';
    }
}
