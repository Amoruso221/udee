package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    //Cliente 1-1

    //Mediciones 1-N

    //Facturas N-1

    //Medidor
}
