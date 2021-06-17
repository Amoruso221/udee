package edu.utn.udee.Udee.service.backoffice;

import edu.utn.udee.Udee.domain.*;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final MeterService meterService;

    @Autowired
    public BillService(BillRepository billRepository, MeterService meterService) {
        this.billRepository = billRepository;
        this.meterService = meterService;
    }

    public List<Bill> createAllBills(List<Meter> meterList) throws MeterNotExistsException {

        List<Bill> newBillList = new ArrayList<>();

        //verificar que el medidor tenga mediciones
        for (Meter meter: meterList) {

            List<Measurement> measurementList = meterService.getUnbilledMeasurements(meter);

                meterService.setBilledMeasumerent(meter);

                Double totalMeasurement = measurementList.stream().mapToDouble(x->x.getKwh()).sum();

                Bill newBill = Bill.builder().
                        fullName(meter.getAddress().getClient().getName() + " " + meter.getAddress().getClient().getSurname()).
                        address(meter.getAddress().getAddress()).
                        city(meter.getAddress().getCity()).
                        meterSerialNumber(meter.getSerialNumber()).
                        firstMeasurement(measurementList.get(0).getKwh()).
                        lastMeasurement(measurementList.get(measurementList.size() - 1).getKwh()).
                        firstMeasurementDateTime(measurementList.get(0).getDateTime()).
                        lastMeasurementDateTime(measurementList.get(measurementList.size() - 1).getDateTime()).
                        totalMeasurementKwh(totalMeasurement).
                        rate(meter.getAddress().getRate().getDescription()).
                        totalAmount(totalMeasurement * meter.getAddress().getRate().getAmount()).
                        build();

                newBillList.add(newBill);
                billRepository.save(newBill);
        }

        return newBillList;
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

//    public void createAddressBill(Integer idAddress)
//            throws AddressNotExistsException {
//        Address address = addressService.findAddressById(idAddress);
//        Meter meter = meterService.getByAddress(address);
//        List<Measurement> measurements= meterService.getUnbilledMeasurements(meter);
//        Rate rate = address.getRate();
//        Double totalAmount = measurements.stream().mapToDouble(...) * rate;
//        Bill newBill = Bill.builder().
//                client(address.getClient()).
//                address(address).
//                meter(meter).
//                measurements(measurements).
//                rate(rate).
//                totalAmount(totalAmount).
////        Double totalAmount; // = totalMeasurement * amountRate
////        Boolean paid;
//                build();
//    }


    public Page getAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    public Bill getById(Integer id)
            throws BillNotExistsException {
        return billRepository.findById(id).
                orElseThrow(BillNotExistsException::new);
    }

    public List<Bill> addressDebt(Address address) {
        List<Bill> unpaidBills = billRepository.findUnpaidByAddress(address.getAddress());
//        List<Bill> bills = billRepository.findByAddress(address.getAddress());
//        List<Bill> unpaidBills = bills.stream().
//                filter(x -> x.getPaid() == false).collect(Collectors.toList());
        return unpaidBills;
    }

    public List<Bill> clientBedt(Client client) {
        List<Address> addresses = client.getAddress();
        List<Bill> unpaidBills = new ArrayList<>();
        for (Address address : addresses){
            List<Bill> unpaidAddressBills = addressDebt(address);
            for (Bill bill : unpaidAddressBills){
                unpaidAddressBills.add(bill);
            }
        }
        return unpaidBills;
    }

    public void deleteById(Integer id)
            throws BillNotExistsException {
        if (billRepository.existsById(id))
            billRepository.deleteById(id);
        else throw new BillNotExistsException();
    }
}
