package com.ega.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ega.api.repository.ClientRepository;
import com.ega.api.repository.CompteRepository;

@SpringBootApplication
public class ApiApplication {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CompteRepository compteRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

