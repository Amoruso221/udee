package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.utn.udee.Udee.domain.Meter;

@Service
public class MeterService {

    private final MeterRepository measurerRepository;
    private final MeasurementService measurementService;
    private final AddressService addressService;
    private final BillService billService;

    @Autowired
    public MeterService(MeterRepository measurerRepository, MeasurementService measurementService, AddressService addressService, BillService billService) {
        this.measurerRepository = measurerRepository;
        this.measurementService = measurementService;
        this.addressService = addressService;
        this.billService = billService;
    }



    public Meter addMeter(Meter meter) {
        meter.setMeasurement(0.0);
        return measurerRepository.save(meter);
    }

    public Page<Meter> getAll(Pageable pageable) {
        return measurerRepository.findAll(pageable);
    }

    public Meter getBySerialNumber(Integer serialNumber)
            throws MeterNotExistsException {
        return measurerRepository.findById(serialNumber).
                orElseThrow(MeterNotExistsException::new);
    }

    public void deleteBySerialNumber(Integer serialNumber)
            throws MeterNotExistsException {
        if (measurerRepository.existsById(serialNumber))
            measurerRepository.deleteById(serialNumber);
        else throw new MeterNotExistsException();
    }

    public void addMeasurementToMeter(Integer serialNumber, Integer idMeasurement)
            throws MeterNotExistsException, MeasurementNotExistsException {
        if (measurerRepository.existsById(serialNumber)) {
            Meter meter = this.getBySerialNumber(serialNumber);
            Measurement measurement = measurementService.getById(idMeasurement);
            meter.setMeasurement(meter.getMeasurement() + measurement.getKwh());
            measurement.setMeter(meter);
            meter.getMeasurements().add(measurement);
            measurerRepository.save(meter);
        }
        else throw new MeterNotExistsException();
    }

    public void addAddressToMeter(Integer serialNumber, Integer id)
            throws MeterNotExistsException, AddressNotExistsException {
        if (measurerRepository.existsById(serialNumber)) {
            Meter meter = this.getBySerialNumber(serialNumber);
            Address address = addressService.findAddressById(id);
            meter.setAddress(address);
            measurerRepository.save(meter);
        }
        else throw new MeterNotExistsException();
    }

    public void addBillToMeter(Integer serialNumber, Integer id)
            throws MeterNotExistsException, BillNotExistsException {
        if (measurerRepository.existsById(serialNumber)){
            Meter meter = this.getBySerialNumber(serialNumber);
            Bill bill = billService.getById(id);
            bill.setMeter(meter);
            meter.getBills().add(bill);
            measurerRepository.save(meter);
        }
        else throw new MeterNotExistsException();
    }
}
