package com.gestionstock3.gestionstock.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venteId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article articleVendu;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int quantiteVendue;
    private double prixTotal;

    // Getters and Setters

}
