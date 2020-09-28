package com.ppd.mubler.model.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Mubler est l'utilisateur qui réponds aux courses et transporte les colis
 */

@Entity
public class Mubler extends User{

    public Mubler(){super();}

    public Mubler(@NotNull String email, @NotNull String firstName, @NotNull String lastName, @NotNull String password, @NotNull String phoneNumber, boolean isMubler) {
        super(email, firstName, lastName, password, phoneNumber, isMubler);
    }
}
