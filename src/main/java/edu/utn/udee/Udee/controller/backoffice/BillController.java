package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.dto.BillDto;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.backoffice.BillService;
import edu.utn.udee.Udee.service.backoffice.ClientService;
import edu.utn.udee.Udee.service.backoffice.MeterService;
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
    private final AddressService addressService;
    private final ClientService clientService;

    @Autowired
    public BillController(BillService billService, MeterService meterService, ModelMapper modelMapper, AddressService addressService, ClientService clientService) {
        this.billService = billService;
        this.meterService = meterService;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
        this.clientService = clientService;
    }

    //***CREATE ALL***//
    @GetMapping(value = "generate", produces = "application/json")
    public ResponseEntity<List<BillDto>> createAllBills() throws MeterNotExistsException {
        List<Meter> meterList = meterService.getAll();
        List<Bill> billsList = billService.createAllBills(meterList);
        List<BillDto> billsDtoList = listBillToDto(billsList);
        return ResponseEntity.
                status(billsDtoList.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).
                body(billsDtoList);
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

    //***GET ALL***//
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BillDto>> getAll(Pageable pageable){
        Page page = billService.getAll(pageable);
        return ResponseEntity.
                status(page.getTotalElements() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).
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

    //***ADDRESS DEBT***//
    @GetMapping(path = "/addresses/debt/{idAddress}", produces = "application/json")
    public ResponseEntity<List<BillDto>> addressDebt (@PathVariable Integer idAddress)
            throws AddressNotExistsException {
        Address address = addressService.findAddressById(idAddress);
        List<Bill> unbilledBills = billService.addressDebt(address);
        List<BillDto> unbilledBillsDto = listBillToDto(unbilledBills);
        return ResponseEntity.
                status(unbilledBillsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).
                body(unbilledBillsDto);
    }

    //***CLIENT DEBT***//
    @GetMapping(path = "/clients/debt/{idClient}", produces = "application/json")
    public ResponseEntity<List<BillDto>> clientDebt (@PathVariable Integer idClient)
            throws ClientNotExistsException {
        Client client = clientService.findClientById(idClient);
        List<Bill> unbilledBills = billService.clientDebt(client);
        List<BillDto> unbilledBillsDto = listBillToDto(unbilledBills);
        return ResponseEntity.
                status(unbilledBillsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).
                body(unbilledBillsDto);
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

    private List<BillDto> listBillToDto (List<Bill> list){
        return list.stream().
                map(x -> modelMapper.map(x, BillDto.class)).
                collect(Collectors.toList());
    }
}
