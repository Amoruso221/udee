package edu.utn.udee.Udee.controller.backoffice;


import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.dto.ClientDto;
import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.backoffice.ClientService;
import org.modelmapper.ModelMapper;
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
@RequestMapping(value = "/api/backoffice/clients")
public class ClientController {

    private final AddressService addressService;
    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController (AddressService addressService, ClientService clientService, ModelMapper modelMapper){
        this.addressService = addressService;
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addClient(@RequestBody ClientDto clientDto) throws ClientExistsException {
        Client newClient = clientService.addClient(modelMapper.map(clientDto, Client.class));
        URI location = returnClientLocation(newClient);

        return ResponseEntity.created(location).build();
    }


    @PutMapping(path = "/{dni}", produces = "application/json")
    public ResponseEntity editClient(@RequestBody ClientDto clientDto, @PathVariable Integer dni) throws ClientNotExistsException {
        Client editedClient = clientService.editClient(modelMapper.map(clientDto, Client.class), dni);
        URI location = returnClientLocation(editedClient);

        return ResponseEntity.created(location).build();
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ClientDto>> allClients(Pageable pageable){
        Page page = clientService.allClients(pageable);
        return response(page);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity deleteClientById(@PathVariable(value = "id") Integer id) throws ClientNotExistsException {
        clientService.deleteClientById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private URI returnClientLocation(Client client){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();

        return location;
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    /*@GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<ClientDto> findClientById(@PathVariable(value = "id") Integer id) throws ClientNotExistsException {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(ClientDto.from(client));
    }*/

    // privados

    /*private ResponseEntity response(List list, Page page) {
        HttpStatus status = !list.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }*/


    /*private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }*/

}
