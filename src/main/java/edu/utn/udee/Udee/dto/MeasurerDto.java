package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Measurement;
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
    private List<Measurement> measurements;

    //Domicilio 1-1

    //Facturas N-1



    public static MeasurerDto from(Measurer measurer){
        return MeasurerDto.builder().
                serialNumber(measurer.getSerialNumber()).
                brand(measurer.getBrand()).
                model(measurer.getModel()).
                measurement(measurer.getMeasurement()).
                measurements(measurer.getMeasurements()).
                build();
    }

}
