package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {

    Integer id;
    Client client;
    Address address;
    Meter meter;
    List<Measurement> measurements;
    Rate rate;
    Double totalAmount; // = totalMeasurement * amountRate
    Boolean paid;

        public static BillDto from(Bill bill) {
            return BillDto.builder().
                    id(bill.getId()).
                    client(bill.getClient()).
                    address(bill.getAddress()).
                    meter(bill.getMeter()).
                    measurements(bill.getMeasurements()).
                    rate(bill.getRate()).
                    totalAmount(bill.getTotalAmount()).
                    paid(bill.getPaid()).
                    build();

        }
}
