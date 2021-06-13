package edu.utn.udee.Udee.service.client;

import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.repository.AddressRepository;
import edu.utn.udee.Udee.repository.ClientBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientBillService {

    private final ClientBillRepository clientBillRepository;

    @Autowired
    public ClientBillService (ClientBillRepository clientBillRepository){
        this.clientBillRepository = clientBillRepository;
    }

    public List<Bill> getBillsBetweenDates(LocalDate startDate, LocalDate endDate, Integer dni) {
        return clientBillRepository.getAllBetweenDates(startDate, endDate, dni);
    }
}
