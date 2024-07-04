package com.gestionstock3.gestionstock.Controller;
import com.gestionstock3.gestionstock.Modele.Client;
import com.gestionstock3.gestionstock.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
//@CrossOrigin(origins = "http://localhost:8080")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/read")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Client createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            Client existingClient = client.get();
            existingClient.setClientNom(clientDetails.getClientNom());
            existingClient.setClientPrénom(clientDetails.getClientPrénom());
            existingClient.setClientEmail(clientDetails.getClientEmail());
            existingClient.setClientTelephone(clientDetails.getClientTelephone());
            Client updatedClient = clientService.saveClient(existingClient);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            clientService.deleteClient(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
