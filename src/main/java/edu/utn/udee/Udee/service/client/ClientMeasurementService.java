package edu.utn.udee.Udee.service.client;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.projections.KwhAndAmount;
import edu.utn.udee.Udee.repository.ClientMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientMeasurementService {

    private final ClientMeasurementRepository clientMeasurementRepository;

    @Autowired
    public ClientMeasurementService(ClientMeasurementRepository clientMeasurementRepository) {
        this.clientMeasurementRepository = clientMeasurementRepository;
    }



    public KwhAndAmount getTotalKwhAndAmountBetweenDates(Integer client_id, LocalDate startDate, LocalDate endDate) {
        return clientMeasurementRepository.findTotalKwhAndAmountByClient(client_id, startDate, endDate);
    }

    public List<Measurement> getBetweenDates(Integer client_id, LocalDate startDate, LocalDate endDate) {
        return clientMeasurementRepository.findBetweenDates(client_id, startDate, endDate);
    }
}
