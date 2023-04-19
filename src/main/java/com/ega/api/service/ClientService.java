package com.ega.api.service;

import com.ega.api.entity.Client;
import com.ega.api.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }


public Client getClientById(Integer id) {
    Optional<Client> optionalClient = clientRepository.findById(id);
    if (optionalClient.isPresent()) {
        return optionalClient.get();
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce client n'existe pas");
    }
}

    public ResponseEntity<String> createClient(Client client) {
        String email = client.getEmail();
        if (clientRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Cet email est déjà associé à un compte");
        }
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.ok(String.valueOf(savedClient));
    }


public Client updateClient(Integer id, Client client) {
    Optional<Client> optionalClient = clientRepository.findById(id);
    if (!optionalClient.isPresent()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce client n'existe pas");
    }
    Client existingClient = optionalClient.get();
    if (!existingClient.getEmail().equals(client.getEmail()) && clientRepository.findByEmail(client.getEmail()).isPresent()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email a été déjà associé à un compte");
    }
    existingClient.setNom(client.getNom());
    existingClient.setPrenoms(client.getPrenoms());
    existingClient.setEmail(client.getEmail());
    existingClient.setAdresse(client.getAdresse());
    existingClient.setTelephone(client.getTelephone());
    existingClient.setSexe(client.getSexe());
    existingClient.setNationalite(client.getNationalite());
    existingClient.setDateNaissance(client.getDateNaissance());
    return clientRepository.save(existingClient);
}

    public ResponseEntity<String> deleteClient(Integer id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.deleteById(id);
            return ResponseEntity.ok("Le client a été supprimé");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce client n'existe pas");
        }
    }
}
