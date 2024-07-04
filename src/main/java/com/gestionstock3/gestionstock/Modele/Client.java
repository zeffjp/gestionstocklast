package com.gestionstock3.gestionstock.Modele;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    private String clientNom;
    private String clientPrénom;
    private String clientEmail;
    private String clientAdresse;
    private String clientTelephone;

    // Getters and Setters

}
