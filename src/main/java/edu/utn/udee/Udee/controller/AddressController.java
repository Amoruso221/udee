package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.exceptions.AddressExistsException;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
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

@RestController
@RequestMapping(value = "/api/addresses")
public class AddressController {

    private final AddressService addressService;
    private final ClientService clientService;

    @Autowired
    public AddressController(AddressService addressService, ClientService clientService){
        this.addressService = addressService;
        this.clientService = clientService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addAddress(@RequestBody Address address) throws AddressExistsException {
        Address newAddress = addressService.addAddress(address);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddress.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Address>> allAddress(Pageable pageable){
        Page page = addressService.allAddress(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Address> findAddressById(@PathVariable(value = "id") Integer id) throws AddressNotExistsException {
        Address client = addressService.findAddressById(id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity deleteAddressById(@PathVariable(value = "id") Integer id) throws AddressNotExistsException {
        addressService.deleteAddressById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
