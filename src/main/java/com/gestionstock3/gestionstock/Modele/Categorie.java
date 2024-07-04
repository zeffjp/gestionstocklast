package com.gestionstock3.gestionstock.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categorieId;

    private String categorieNom;
    private String categorieDescription;

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles;
}
