package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.dto.AddressDto;
import edu.utn.udee.Udee.exceptions.AddressExistsException;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.exceptions.RateNotExistsException;
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
@RequestMapping(value = "/api/backoffice/addresses")
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper modelMapper){
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addAddress(@RequestBody AddressDto addressDto)
            throws AddressExistsException, ClientNotExistsException, RateNotExistsException {
        Address newAddress = addressService.addAddress(modelMapper.map(addressDto, Address.class));
        URI location = returnAddressLocation(newAddress);

        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity editAddress(@RequestBody AddressDto addressDto, @PathVariable Integer id) throws AddressNotExistsException {
        Address editedAddress = addressService.editAddress(modelMapper.map(addressDto, Address.class), id);
        URI location = returnAddressLocation(editedAddress);

        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AddressDto>> allAddress(Pageable pageable){
        Page page = addressService.allAddress(pageable);
        return response(page);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity deleteAddressById(@PathVariable(value = "id") Integer id) throws AddressNotExistsException {
        addressService.deleteAddressById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private URI returnAddressLocation(Address address){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(address.getId())
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

    /*@GetMapping(value = "client/{id}", produces = "application/json")
    public ResponseEntity<List<AddressDto>> getAddressesByClient(@PathVariable(value = "id") Integer id) {
        List<Address> addresses = addressService.findAddressesByClient(id);
        List<AddressDto> addressDtoList =
                addresses.stream().map(o -> AddressDto.builder().
                        id(o.getId()).
                        address(o.getAddress()).
                        city(o.getCity()).
                        state(o.getState()).
                        country(o.getCountry()).
                        build()).
                        collect(Collectors.toList());
        return ResponseEntity.status(addressDtoList.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(addressDtoList);
    }*/

    /*@GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<AddressDto> findAddressById(@PathVariable(value = "id") Integer id) throws AddressNotExistsException {
        Address address = addressService.findAddressById(id);
        return ResponseEntity.ok(AddressDto.from(address));
    }*/


}
