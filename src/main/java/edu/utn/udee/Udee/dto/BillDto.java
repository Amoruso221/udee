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
    String fullName;
    String address;
    String city;
    Integer meterSerialNumber;
    Double firstMeasurement;
    Double lastMeasurement;
    LocalDateTime firstMeasurementDateTime;
    LocalDateTime lastMeasurementDateTime;
    Double totalMeasurementKwh;
    String rate; //rate type
    Double totalAmount;
    Boolean paid;

        public static BillDto from(Bill bill) {
            return BillDto.builder().
                    id(bill.getId()).
                    fullName(bill.getFullName()).
                    address(bill.getAddress()).
                    city(bill.getCity()).
                    meterSerialNumber(bill.getMeterSerialNumber()).
                    firstMeasurement(bill.getFirstMeasurement()).
                    lastMeasurement(bill.getLastMeasurement()).
                    firstMeasurementDateTime(bill.getFirstMeasurementDateTime()).
                    lastMeasurementDateTime(bill.getLastMeasurementDateTime()).
                    totalMeasurementKwh(bill.getTotalMeasurementKwh()).
                    rate(bill.getRate()).
                    totalAmount(bill.getTotalAmount()).
                    paid(bill.getPaid()).
                    build();

        }
}
