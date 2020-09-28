package com.ppd.mubler.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class VehiculeType {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @NotNull
    private float minWidth;

    @NotNull
    private float minHeigth;

    @NotNull
    private float minDepth;

    @NotNull
    private float minWeigth;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public float getMinHeigth() {
        return minHeigth;
    }

    public void setMinHeigth(float minHeigth) {
        this.minHeigth = minHeigth;
    }

    public float getMinDepth() {
        return minDepth;
    }

    public void setMinDepth(float minDepth) {
        this.minDepth = minDepth;
    }

    public float getMinWeigth() {
        return minWeigth;
    }

    public void setMinWeigth(float minWeigth) {
        this.minWeigth = minWeigth;
    }
}
