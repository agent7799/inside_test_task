package com.agent7799.springboot.security.firstsecurityapp.model;


import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "message")
    private String message;

//    @ManyToOne
//    @JoinColumn(name = "username")
    @Column(name = "username")
    private String username;

    public Message() {
    }

    public Message(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

