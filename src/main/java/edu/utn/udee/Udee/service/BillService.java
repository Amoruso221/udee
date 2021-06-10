package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.*;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ClientService clientService;
    private final AddressService addressService;
    private final MeterService meterService;

    @Autowired
    public BillService(BillRepository billRepository, ClientService clientService, AddressService addressService, MeterService meterService) {
        this.billRepository = billRepository;
        this.clientService = clientService;
        this.addressService = addressService;
        this.meterService = meterService;
    }



    /*public Bill addBill(Bill bill)
            throws ClientNotExistsException, MeterNotExistsException {
        Client client = clientService.findClientById(bill.getDniClient());
        Address address = addressService.findAddressByAddress(bill.getAddress());
        Meter meter = meterService.getBySerialNumber(bill.getSerialNumberMeter());
        List<Measurement> measurements = meter.listUnbilledMeasurements(bill.getId());
        Measurement initialMeasurement = measurements.get(0);
        Measurement finalMeasurement = measurements.get(measurements.size()-1);
        Double totalMeasurement = measurements.stream().mapToDouble(x->x.getKwh()).sum();
        Rate rate = address.getRate();
        Bill newBill = Bill.builder().
                id(bill.getId()).
                dniClient(client.getDni()).
                fullNameClient(client.getName() + " " + client.getSurname()).
                address(address.getAddress()).
                city(address.getCity()).
                state(address.getState()).
                country(address.getCountry()).
                serialNumberMeter(meter.getSerialNumber()).
                initialMeasurement(initialMeasurement.getKwh()).
                finalMeasurement(finalMeasurement.getKwh()).
                totalMeasurement(totalMeasurement).
                dateTimeInitialmeasurement(initialMeasurement.getDateTime()).
                dateTimeFinalmeasurement(finalMeasurement.getDateTime()).
                descriptionRate(rate.getDescription()).
                amountRate(rate.getAmount()).
                totalAmount(totalMeasurement * rate.getAmount()).
                build();
        return billRepository.save(newBill);
    }*/

    public Page getAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    public Bill getById(Integer id)
            throws BillNotExistsException {
        return billRepository.findById(id).
                orElseThrow(BillNotExistsException::new);
    }

    public void deleteById(Integer id)
            throws BillNotExistsException {
        if (billRepository.existsById(id))
            billRepository.deleteById(id);
        else throw new BillNotExistsException();
    }
}
