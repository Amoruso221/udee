package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.dto.BillDto;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.BillService;
import edu.utn.udee.Udee.service.MeterService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/backoffice/bills")
public class BillController {

    private final BillService billService;
    private final MeterService meterService;
    private final ModelMapper modelMapper;

    @Autowired
    public BillController(BillService billService, MeterService meterService, ModelMapper modelMapper) {
        this.billService = billService;
        this.meterService = meterService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "generate", produces = "application/json")
    public ResponseEntity<List<BillDto>> createAllBills() throws MeterNotExistsException {
        List<Meter> meterList = meterService.getAll();
        List<Bill> billsList = billService.createAllBills(meterList);
        List<BillDto> billsDtoList = billsList.stream().map(x -> modelMapper.map(x, BillDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(billsDtoList.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(billsDtoList);
        }


        //***ADD NEW***//
    /*@PostMapping(consumes = "application/json")
    public ResponseEntity addBill (@RequestBody BillDto billDto)
            throws ClientNotExistsException, MeterNotExistsException{
        Bill newbill = billService.addBill(modelMapper.map(billDto,Bill.class));
        return ResponseEntity.created(getLocation(newbill)).build();
    }
            throws MeterNotExistsException {
        Bill newbill = billService.addBill(Bill.builder().
                meter(meterService.getBySerialNumber(billDto.getMeter().getSerialNumber())).
                build());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newbill.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }*/

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BillDto>> getAll(Pageable pageable){
        Page page = billService.getAll(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<BillDto> getById (@PathVariable Integer id)
            throws BillNotExistsException {
        Bill bill = billService.getById(id);
        return ResponseEntity.ok(BillDto.from(bill));
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteBill(@PathVariable Integer id)
            throws BillNotExistsException{
        billService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private URI getLocation (Bill bill) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bill.getId())
                .toUri();
    }

}
