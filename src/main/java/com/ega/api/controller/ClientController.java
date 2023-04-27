package com.ega.api.controller;

import com.ega.api.entity.Client;
import com.ega.api.repository.ClientRepository;
import com.ega.api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import java.util.Optional;


@RestController
@RequestMapping("/clients")

public class ClientController {
    @Autowired
    ClientService clientService;



    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/select/{id}")
    public Client getClientById(@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @PostMapping("/create")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping("/update/{id}")
    public Client updateClient(@PathVariable Integer id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Integer id) {
        return clientService.deleteClient(id);
    }



}
