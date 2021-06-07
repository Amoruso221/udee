package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Meter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeterDto {

    private Integer serialNumber;
    private String brand;
    private String model;
    private Double measurement;
    private List<MeasurementDto> measurements;
    private AddressDto address;
    private List<BillDto> bills;



    public static MeterDto from(Meter meter){
        return MeterDto.builder().
                serialNumber(meter.getSerialNumber()).
                brand(meter.getBrand()).
                model(meter.getModel()).
                measurement(meter.getMeasurement()).
                //measurements(meter.getMeasurements()).
                address(AddressDto.from(meter.getAddress())).
                //Bills
                build();
    }

}
