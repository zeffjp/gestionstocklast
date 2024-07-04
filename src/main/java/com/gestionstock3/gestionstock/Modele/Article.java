package com.gestionstock3.gestionstock.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String articleNom;
    private String articleDescription;
    private double articlePrix;
    private int articleQuantite;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToOne
    private Image image;
}
