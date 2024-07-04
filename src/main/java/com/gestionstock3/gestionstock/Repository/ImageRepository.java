package com.gestionstock3.gestionstock.Repository;


import com.gestionstock3.gestionstock.Modele.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}