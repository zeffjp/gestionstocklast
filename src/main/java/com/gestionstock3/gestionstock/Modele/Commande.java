package com.gestionstock3.gestionstock.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commandeNumero;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDate commandeDate;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private int commandeQuantite;
    private double commandePrixtotal;
    private String commandeStatut;
}
