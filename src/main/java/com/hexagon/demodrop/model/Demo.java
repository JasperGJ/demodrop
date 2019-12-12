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
    private Long length;
    private String type;
    private Date uploaded;

    private Date reviewed;
    private long reviewedBy;
    private String comment;
    private String status;

    private boolean deleted;
    private long deletedBy;

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

    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    public Date getReviewed() {
        return reviewed;
    }

    public void setReviewed(Date reviewed) {
        this.reviewed = reviewed;
    }

    public long getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(long reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}