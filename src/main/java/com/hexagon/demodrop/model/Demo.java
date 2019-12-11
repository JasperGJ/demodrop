package com.hexagon.demodrop.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Demo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String fileName;
    @Lob
    private byte[] audio;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Demo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}