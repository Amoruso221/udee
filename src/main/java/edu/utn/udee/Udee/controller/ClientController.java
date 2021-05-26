package edu.utn.udee.Udee.controller;


import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.service.AddressService;
import edu.utn.udee.Udee.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {

    private final AddressService addressService;
    private final ClientService clientService;

    @Autowired
    public ClientController (AddressService addressService, ClientService clientService){
        this.addressService = addressService;
        this.clientService = clientService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addClient(@RequestBody Client client) throws ClientExistsException {
        Client newClient = clientService.addClient(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newClient.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Client> editClient(@RequestBody Client client) throws ClientNotExistsException {
        Client editedClient = clientService.editClient(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(editedClient.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Client>> allClients(Pageable pageable){
        Page page = clientService.allClients(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Client> findClientById(@PathVariable(value = "id") Integer id) throws ClientNotExistsException {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity deleteClientById(@PathVariable(value = "id") Integer id) throws ClientNotExistsException {
        clientService.deleteClientById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
