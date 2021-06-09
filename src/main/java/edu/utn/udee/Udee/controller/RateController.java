package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Rate;
import edu.utn.udee.Udee.dto.RateDto;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.RateExistsException;
import edu.utn.udee.Udee.exceptions.RateNotExistsException;
import edu.utn.udee.Udee.service.RateService;
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
@RequestMapping("/api/rates")
public class RateController {

    private final RateService rateService;
    private final ModelMapper modelMapper;

    @Autowired
    public RateController(RateService rateService, ModelMapper modelMapper) {
        this.rateService = rateService;
        this.modelMapper = modelMapper;
    }

    private URI getLocation (Rate rate) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rate.getId())
                .toUri();
    }



    //***ADD NEW***//
    @PostMapping(consumes = "application/json")
    public ResponseEntity addRate (@RequestBody RateDto rateDto)
            throws RateExistsException {
        Rate newRate = rateService.addRate(modelMapper.map(rateDto, Rate.class));
        return ResponseEntity.created(getLocation(newRate)).build();
    }

    //***GET ALL***//
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<RateDto>> getAll(Pageable pageable){
        Page page = rateService.getAll(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    //***GET BY ID***//
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RateDto> getById (@PathVariable Integer id)
            throws RateNotExistsException {
        Rate rate = rateService.getById(id);
        return ResponseEntity.ok(RateDto.from(rate));
    }

    //***DELETE BY ID***//
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteRate(@PathVariable Integer id)
            throws RateNotExistsException {
        rateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD ADDRESS***//
    @PutMapping(path = "/{idRate}/addresses/{idAddress}", produces = "application/json")
    public ResponseEntity addAddressToRate (@PathVariable Integer idRate, @PathVariable Integer idAddress)
            throws RateNotExistsException, AddressNotExistsException {
        rateService.addAddressToRate(idRate, idAddress);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
