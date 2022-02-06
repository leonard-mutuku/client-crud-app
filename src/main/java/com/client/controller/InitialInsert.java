/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.controller;

import com.client.model.Client;
import com.client.respository.ClientRepository;
import com.github.javafaker.Faker;
import java.util.Locale;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author leonard
 */
@Component
public class InitialInsert implements CommandLineRunner {
    
    private final ClientRepository clientRepository;
    private final Faker faker = new Faker(Locale.getDefault());
    
    public InitialInsert(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    @Override
    public void run(String ... args) {
        for (int i = 0; i < 20; i++) {
            clientRepository.save(new Client(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.phoneNumber().cellPhone()));
        }
    }
}
