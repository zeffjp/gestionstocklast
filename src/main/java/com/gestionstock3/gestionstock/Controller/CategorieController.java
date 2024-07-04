package com.gestionstock3.gestionstock.Controller;
import com.gestionstock3.gestionstock.Modele.Categorie;
import com.gestionstock3.gestionstock.Service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
//@CrossOrigin(origins = "http://localhost:8080")

public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping("/read")
    public List<Categorie> getAllCategories() {
        return categorieService.getAllCategories();
    }

    @GetMapping("read/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        Optional<Categorie> categorie = categorieService.getCategorieById(id);
        if (categorie.isPresent()) {
            return ResponseEntity.ok(categorie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Categorie createCategorie(@RequestBody Categorie categorie) {
        return categorieService.saveCategorie(categorie);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorieDetails) {
        Optional<Categorie> categorie = categorieService.getCategorieById(id);
        if (categorie.isPresent()) {
            Categorie existingCategorie = categorie.get();
            existingCategorie.setCategorieNom(categorieDetails.getCategorieNom());
            existingCategorie.setCategorieDescription(categorieDetails.getCategorieDescription());
            Categorie updatedCategorie = categorieService.saveCategorie(existingCategorie);
            return ResponseEntity.ok(updatedCategorie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        Optional<Categorie> categorie = categorieService.getCategorieById(id);
        if (categorie.isPresent()) {
            categorieService.deleteCategorie(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

