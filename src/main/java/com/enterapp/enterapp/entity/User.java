package com.enterapp.enterapp.entity;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length=30)
    private String name;

    @Column(nullable = false, length = 15)
    private long phone;

    @Column(nullable = true)
    private String profilePic;  // Field for storing the profile picture file name

    // Constructor with profilePic
    public User(int id, long phone, String profilePic) {
        this.id = id;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    // Default constructor
    public User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPhone() {
        return phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
