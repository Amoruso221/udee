package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.dto.BillDto;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
import edu.utn.udee.Udee.service.BillService;
import edu.utn.udee.Udee.service.MeasurerService;
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
@RequestMapping("/api/bill")
public class BillController {

    private final BillService billService;
    private final MeasurerService measurerService;
    private final ModelMapper modelMapper;

    @Autowired
    public BillController(BillService billService, MeasurerService measurerService, ModelMapper modelMapper) {
        this.billService = billService;
        this.measurerService = measurerService;
        this.modelMapper = modelMapper;
    }



    //***ADD NEW***//
    @PostMapping(consumes = "application/json")
    public ResponseEntity addBill (@RequestBody BillDto billDto)
            throws MeasurerNotExistsException {
        Bill newbill = billService.addBill(Bill.builder().
                measurer(measurerService.getBySerialNumber(billDto.getMeasurer().getSerialNumber())).
                build());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newbill.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //***GET ALL***//
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BillDto>> getAll(Pageable pageable){
        Page page = billService.getAll(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    //***GET BY ID***//
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<BillDto> getById (@PathVariable Integer id)
            throws BillNotExistsException {
        Bill bill = billService.getById(id);
        return ResponseEntity.ok(BillDto.from(bill));
    }

    //***DELETE BY ID***//
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteBill(@PathVariable Integer id)
            throws BillNotExistsException{
        billService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD MEASUREMENT***//
    @PutMapping(path = "/{idBill}/measurement/{idMeasurement}", produces = "application/json")
    public ResponseEntity addMeasurementToMeasurer (@PathVariable Integer idBill, @PathVariable Integer idMeasurement)
            throws BillNotExistsException, MeasurementNotExistsException {
        billService.addMeasurementToBill(idBill, idMeasurement);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
