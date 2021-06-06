package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final MeasurementService measurementService;

    @Autowired
    public BillService(BillRepository billRepository, MeasurementService measurementService) {
        this.billRepository = billRepository;
        this.measurementService = measurementService;
    }



    public Bill addBill(Bill bill) {
        bill.setAmount(0.0);
        return billRepository.save(bill);
    }

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

    public void addMeasurementToBill(Integer idBill, Integer idMeasurement)
            throws BillNotExistsException, MeasurementNotExistsException {
        if (billRepository.existsById(idBill)) {
            Bill bill = this.getById(idBill);
            Measurement measurement = measurementService.getById(idMeasurement);
            bill.setAmount(bill.getAmount() + (measurement.getKwh() * bill.getMeter().getAddress().getRate().getAmount()));
            measurement.setBill(bill);
            bill.getMeasurements().add(measurement);
            billRepository.save(bill);
        }
        else throw new BillNotExistsException();
    }
}
