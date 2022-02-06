/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.controller;

import com.client.model.Client;
import com.client.respository.ClientRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonard
 */
@RestController
@RequestMapping("/clients")
public class ClientsController {
    
    private final ClientRepository clientRepository;
    
    @Autowired
    private SessionController sessionController;
    
    public ClientsController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    private Map fetchClientsData(Integer offset, Integer limit, String filter) {
        Sort sort = Sort.by("id").descending();
        int size = (int) Math.ceil(offset /limit);
        Pageable pageable = PageRequest.of(size, limit, sort);
        Page page;
        if (filter == null || filter.equals("null")) {
            page = clientRepository.findAll(pageable);
        } else {
            page = clientRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(filter, filter, filter, pageable);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("clients", page.getContent());
        response.put("size", page.getTotalElements());
        response.put("pages", page.getTotalPages());
        return response;
    }
    
    @GetMapping
    public ResponseEntity fetchClients(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String filter
    ) {
        sessionController.getLoggedInUser(false);
        Map response = fetchClientsData(offset, limit, filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        sessionController.getLoggedInUser(false);
        return clientRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    
    @PostMapping
    public ResponseEntity createClient(@RequestBody Client client) throws URISyntaxException {
        sessionController.getLoggedInUser(false);
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Client client) {
        sessionController.getLoggedInUser(false);
        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setEmail(client.getEmail());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        currentClient = clientRepository.save(client);
        
        return ResponseEntity.ok(currentClient);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(
            @PathVariable Long id,
            @RequestParam int offset,
            @RequestParam int limit,
            @RequestParam(required = false) String filter) {
        sessionController.getLoggedInUser(false);
        clientRepository.deleteById(id);
        int items = 1;
        int start = (offset + limit) - items;
        Map response = fetchClientsData((start), items, filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
