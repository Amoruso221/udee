package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeterService {

    private final MeterRepository meterRepository;
    private final MeasurementService measurementService;
    private final AddressService addressService;
    private final BillService billService;

    @Autowired
    public MeterService(MeterRepository meterRepository, MeasurementService measurementService, AddressService addressService, BillService billService) {
        this.meterRepository = meterRepository;
        this.measurementService = measurementService;
        this.addressService = addressService;
        this.billService = billService;
    }



    public Meter addMeter(Meter meter) {
        meter.setMeasurement(0.0);
        return meterRepository.save(meter);
    }

    public Page<Meter> getAll(Pageable pageable) {
        return meterRepository.findAll(pageable);
    }

    public List<Meter> getAll(){
        return meterRepository.getAll();
    }

    public Meter getBySerialNumber(Integer serialNumber)
            throws MeterNotExistsException {
        return meterRepository.findById(serialNumber).
                orElseThrow(MeterNotExistsException::new);
    }

    public Meter getByAddress(Address address){
        return meterRepository.findByAddress(address);
    }

    public List<Measurement> getUnbilledMeasurements (Meter meter) {
        List<Measurement> unbilledMeasurements = meter.getMeasurements().stream()
                .filter(x -> x.getBilled() == false).collect(Collectors.toList());
//        this.measurements.stream().map(x -> x.setIdBill(idNewBill));
        return unbilledMeasurements;
    }


    public void deleteBySerialNumber(Integer serialNumber)
            throws MeterNotExistsException {
        if (meterRepository.existsById(serialNumber))
            meterRepository.deleteById(serialNumber);
        else throw new MeterNotExistsException();
    }

    public void addMeasurementToMeter(Integer serialNumber, Integer idMeasurement)
            throws MeterNotExistsException, MeasurementNotExistsException {
        if (meterRepository.existsById(serialNumber)) {
            Meter meter = this.getBySerialNumber(serialNumber);
            Measurement measurement = measurementService.getById(idMeasurement);
            meter.setMeasurement(meter.getMeasurement() + measurement.getKwh());
            measurement.setMeter(meter);
            meter.getMeasurements().add(measurement);
            meterRepository.save(meter);
        }
        else throw new MeterNotExistsException();
    }

    public void addAddressToMeter(Integer serialNumber, Integer id)
            throws MeterNotExistsException, AddressNotExistsException {
        if (meterRepository.existsById(serialNumber)) {
            Meter meter = this.getBySerialNumber(serialNumber);
            Address address = addressService.findAddressById(id);
            meter.setAddress(address);
            meterRepository.save(meter);
        }
        else throw new MeterNotExistsException();
    }


}
