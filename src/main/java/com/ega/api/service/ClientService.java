package com.ega.api.service;

import com.ega.api.entity.Client;
import com.ega.api.repository.ClientRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Data
@Service

public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

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

    public Client createClient(Client client) {
        String email = client.getEmail();
        if (clientRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce email existe deja");
        }
        return clientRepository.save(client);
    }


public Client updateClient(Integer id, Client client) {
    Optional<Client> optionalClient = clientRepository.findById(id);
    if (optionalClient.isEmpty()) {
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
