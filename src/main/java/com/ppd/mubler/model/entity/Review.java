package com.ppd.mubler.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private long id;
    private long idConcerned;
    private long idWriter;
    private int rating;
    private String comment;
    private Date timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdConcerned() {
        return idConcerned;
    }

    public void setIdConcerned(long idConcerned) {
        this.idConcerned = idConcerned;
    }

    public long getIdWriter() {
        return idWriter;
    }

    public void setIdWriter(long idWriter) {
        this.idWriter = idWriter;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", idConcerned=" + idConcerned +
                ", idWriter=" + idWriter +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
