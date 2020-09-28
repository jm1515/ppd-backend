package com.ppd.mubler.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class MublerRequest {
    //demande id startcordx startcoordY endcoordX endcoordY date price typevehicule:objet etat

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private float startCoordX;

    @NotNull
    private float startCoordY;

    @NotNull
    private float endCoordX;

    @NotNull
    private float endCoordY;

    @NotNull
    private Date timestamp;

    @NotNull
    private float price;

    @NotNull
    private boolean isOver;

    public MublerRequest() {
    }

    public MublerRequest(@NotNull float startCoordX, @NotNull float startCoordY, @NotNull float endCoordX, @NotNull float endCoordY, @NotNull Date timestamp, @NotNull float price, @NotNull boolean isOver, String vehiculeTypeName, long idRequester, long idAccepter) {
        this.startCoordX = startCoordX;
        this.startCoordY = startCoordY;
        this.endCoordX = endCoordX;
        this.endCoordY = endCoordY;
        this.timestamp = timestamp;
        this.price = price;
        this.isOver = isOver;
        this.vehiculeTypeName = vehiculeTypeName;
        this.idRequester = idRequester;
        this.idAccepter = idAccepter;
    }

    private String vehiculeTypeName;

    private long idRequester;

    private long idAccepter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getStartCoordX() {
        return startCoordX;
    }

    public void setStartCoordX(float startCoordX) {
        this.startCoordX = startCoordX;
    }

    public float getStartCoordY() {
        return startCoordY;
    }

    public void setStartCoordY(float startCoordY) {
        this.startCoordY = startCoordY;
    }

    public float getEndCoordX() {
        return endCoordX;
    }

    public void setEndCoordX(float endCoordX) {
        this.endCoordX = endCoordX;
    }

    public float getEndCoordY() {
        return endCoordY;
    }

    public void setEndCoordY(float endCoordY) {
        this.endCoordY = endCoordY;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getVehiculeTypeName() {
        return vehiculeTypeName;
    }

    public void setVehiculeTypeName(String vehiculeTypeName) {
        this.vehiculeTypeName = vehiculeTypeName;
    }

    public long getIdRequester() {
        return idRequester;
    }

    public void setIdRequester(long idRequester) {
        this.idRequester = idRequester;
    }

    public long getIdAccepter() {
        return idAccepter;
    }

    public void setIdAccepter(long idAccepter) {
        this.idAccepter = idAccepter;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    @Override
    public String toString() {
        return "MublerRequest{" +
                "id=" + id +
                ", startCoordX=" + startCoordX +
                ", startCoordY=" + startCoordY +
                ", endCoordX=" + endCoordX +
                ", endCoordY=" + endCoordY +
                ", timestamp=" + timestamp +
                ", price=" + price +
                ", vehiculeTypeName='" + vehiculeTypeName + '\'' +
                ", idRequester=" + idRequester +
                ", idAccepter=" + idAccepter +
                ", isOver="+ isOver +
                '}';
    }
}
