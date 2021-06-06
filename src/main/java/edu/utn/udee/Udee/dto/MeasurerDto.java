package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Measurer;
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
public class MeasurerDto {

    private Integer serialNumber;
    private String brand;
    private String model;
    private Double measurement;
    private List<MeasurementDto> measurements;
    private AddressDto address;
    private List<BillDto> bills;



    public static MeasurerDto from(Measurer measurer){
        return MeasurerDto.builder().
                serialNumber(measurer.getSerialNumber()).
                brand(measurer.getBrand()).
                model(measurer.getModel()).
                measurement(measurer.getMeasurement()).
                //measurements(measurer.getMeasurements()).
                address(AddressDto.from(measurer.getAddress())).
                //Bills
                build();
    }

}
