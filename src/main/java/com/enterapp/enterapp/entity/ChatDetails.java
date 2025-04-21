package com.enterapp.enterapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class ChatDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name="receiver")
    private User receiver;

    @ManyToOne
    @JoinColumn(name="msg_id")
    private MessageMaster msg_id;

    @Column(name = "delivered", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char delivered;

    @Column(name="seen", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char seen;

    @CreationTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updated_time;

    @Column(name = "deleted", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char deleted;


    public ChatDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public MessageMaster getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(MessageMaster msg_id) {
        this.msg_id = msg_id;
    }

    public char getDelivered() {
        return delivered;
    }

    public void setDelivered(char delivered) {
        this.delivered = delivered;
    }

    public char getSeen() {
        return seen;
    }

    public void setSeen(char seen) {
        this.seen = seen;
    }

    public LocalDateTime getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(LocalDateTime updated_time) {
        this.updated_time = updated_time;
    }

    public char getDeleted() {
        return deleted;
    }

    public void setDeleted(char deleted) {
        this.deleted = deleted;
    }
}
