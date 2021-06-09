package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Bill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {

    private Integer id;
    private Integer dniClient;
    private String fullNameClient;
    private String address;
    private String city;
    private String state;
    private String country;
    private Integer serialNumberMeter;
    private Double initialMeasurement;
    private Double finalMeasurement;
    private Double totalMeasurement;
    private LocalDateTime dateTimeInitialmeasurement;
    private LocalDateTime dateTimeFinalmeasurement;
    private String descriptionRate;
    private Double amountRate;
    private Double totalAmount;

        public static BillDto from(Bill bill) {
            return BillDto.builder().
                    id(bill.getId()).
                    dniClient(bill.getDniClient()).
                    fullNameClient(bill.getFullNameClient()).
                    address(bill.getAddress()).
                    city(bill.getCity()).
                    state(bill.getState()).
                    country(bill.getCountry()).
                    serialNumberMeter(bill.getSerialNumberMeter()).
                    initialMeasurement(bill.getInitialMeasurement()).
                    finalMeasurement(bill.getFinalMeasurement()).
                    totalMeasurement(bill.getTotalMeasurement()).
                    dateTimeInitialmeasurement(bill.getDateTimeInitialmeasurement()).
                    dateTimeFinalmeasurement(bill.getDateTimeFinalmeasurement()).
                    descriptionRate(bill.getDescriptionRate()).
                    amountRate(bill.getAmountRate()).
                    totalAmount(bill.getTotalAmount()).
                    build();
        }


//    private Integer id;
//    private MeterDto meter;
//    private List<MeasurementDto> measurements;
//    private Double amount;
//
//    public static BillDto from(Bill bill){
//        return BillDto.builder().
//                id(bill.getId()).
//                meter(MeterDto.from(bill.getMeter())).
//                //Measurements?
//                amount(bill.getAmount()).
//                build();
//    }

}
