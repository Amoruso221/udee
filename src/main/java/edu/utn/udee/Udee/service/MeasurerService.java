package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
import edu.utn.udee.Udee.repository.MeasurerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.utn.udee.Udee.domain.Measurer;

@Service
public class MeasurerService {

    private final MeasurerRepository measurerRepository;
    private final MeasurementService measurementService;
    private final AddressService addressService;
    private final BillService billService;

    @Autowired
    public MeasurerService(MeasurerRepository measurerRepository, MeasurementService measurementService, AddressService addressService, BillService billService) {
        this.measurerRepository = measurerRepository;
        this.measurementService = measurementService;
        this.addressService = addressService;
        this.billService = billService;
    }



    public Measurer addMeasurer(Measurer measurer) {
        measurer.setMeasurement(0.0);
        return measurerRepository.save(measurer);
    }

    public Page<Measurer> getAll(Pageable pageable) {
        return measurerRepository.findAll(pageable);
    }

    public Measurer getBySerialNumber(Integer serialNumber)
            throws MeasurerNotExistsException {
        return measurerRepository.findById(serialNumber).
                orElseThrow(MeasurerNotExistsException::new);
    }

    public void deleteBySerialNumber(Integer serialNumber)
            throws MeasurerNotExistsException{
        if (measurerRepository.existsById(serialNumber))
            measurerRepository.deleteById(serialNumber);
        else throw new MeasurerNotExistsException();
    }

    public void addMeasurementToMeasurer(Integer serialNumber, Integer idMeasurement)
            throws MeasurerNotExistsException, MeasurementNotExistsException {
        if (measurerRepository.existsById(serialNumber)) {
            Measurer measurer = this.getBySerialNumber(serialNumber);
            Measurement measurement = measurementService.getById(idMeasurement);
            measurer.setMeasurement(measurer.getMeasurement() + measurement.getKwh());
            measurement.setMeasurer(measurer);
            measurer.getMeasurements().add(measurement);
            measurerRepository.save(measurer);
        }
        else throw new MeasurerNotExistsException();
    }

    public void addAddressToMeasurer(Integer serialNumber, Integer id)
            throws MeasurerNotExistsException, AddressNotExistsException {
        if (measurerRepository.existsById(serialNumber)) {
            Measurer measurer = this.getBySerialNumber(serialNumber);
            Address address = addressService.findAddressById(id);
            measurer.setAddress(address);
            measurerRepository.save(measurer);
        }
        else throw new MeasurerNotExistsException();
    }

    public void addBillToMeasurer(Integer serialNumber, Integer id)
            throws MeasurerNotExistsException, BillNotExistsException {
        if (measurerRepository.existsById(serialNumber)){
            Measurer measurer = this.getBySerialNumber(serialNumber);
            Bill bill = billService.getById(id);
            bill.setMeasurer(measurer);
            measurer.getBills().add(bill);
            measurerRepository.save(measurer);
        }
        else throw new MeasurerNotExistsException();
    }
}
