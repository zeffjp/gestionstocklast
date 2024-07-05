package com.gestionstock3.gestionstock.Controller;

import com.gestionstock3.gestionstock.Modele.Vente;
import com.gestionstock3.gestionstock.Service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")

public class VenteController {

    @Autowired
    private VenteService venteService;

    @GetMapping("/read")
    public List<Vente> getAllVentes() {
        return venteService.getAllVentes();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable Long id) {
        Optional<Vente> vente = venteService.getVenteById(id);
        if (vente.isPresent()) {
            return ResponseEntity.ok(vente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Vente createVente(@RequestBody Vente vente) {
        return venteService.saveVente(vente);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @RequestBody Vente venteDetails) {
        Optional<Vente> vente = venteService.getVenteById(id);
        if (vente.isPresent()) {
            Vente existingVente = vente.get();
            existingVente.setClient(venteDetails.getClient());
            existingVente.setArticleVendu(venteDetails.getArticleVendu());
            existingVente.setDate(venteDetails.getDate());
            existingVente.setQuantiteVendue(venteDetails.getQuantiteVendue());
            existingVente.setPrixTotal(venteDetails.getPrixTotal());
            Vente updatedVente = venteService.saveVente(existingVente);
            return ResponseEntity.ok(updatedVente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        Optional<Vente> vente = venteService.getVenteById(id);
        if (vente.isPresent()) {
            venteService.deleteVente(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
