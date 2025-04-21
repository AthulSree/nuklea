package com.enterapp.enterapp.entity;

import jakarta.persistence.*;

@Entity
public class MessageMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type", nullable = false)
    private char type;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT CHARACTER SET utf8mb4")
    private String content;



    public MessageMaster() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
