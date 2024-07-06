package com.gestionstock3.gestionstock.Controller;

import com.gestionstock3.gestionstock.Modele.Commande;
import com.gestionstock3.gestionstock.Service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping("/read")
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> commande = commandeService.getCommandeById(id);
        if (commande.isPresent()) {
            return ResponseEntity.ok(commande.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeService.saveCommande(commande);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        Optional<Commande> commande = commandeService.getCommandeById(id);
        if (commande.isPresent()) {
            Commande existingCommande = commande.get();
            existingCommande.setClient(commandeDetails.getClient());
            existingCommande.setCommandeDate(commandeDetails.getCommandeDate());
            existingCommande.setArticle(commandeDetails.getArticle());
            existingCommande.setCommandeQuantite(commandeDetails.getCommandeQuantite());
            existingCommande.setCommandePrixtotal(commandeDetails.getCommandePrixtotal());
            existingCommande.setCommandeStatut(commandeDetails.getCommandeStatut());
            Commande updatedCommande = commandeService.saveCommande(existingCommande);
            return ResponseEntity.ok(updatedCommande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        Optional<Commande> commande = commandeService.getCommandeById(id);
        if (commande.isPresent()) {
            commandeService.deleteCommande(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
