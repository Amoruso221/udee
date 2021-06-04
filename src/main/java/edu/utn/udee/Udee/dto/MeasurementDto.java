package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Measurer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementDto {

    private Integer idMeasurement;
    private Double kwh;
    private LocalDateTime dateTime;
    private MeasurerDto measurer;

    public static MeasurementDto from(Measurement measurement) {
        Measurer measurer = measurement.getMeasurer();
        return MeasurementDto.builder().
                idMeasurement(measurement.getIdMeasurement()).
                kwh(measurement.getKwh()).
                dateTime(measurement.getDateTime()).
//                measurer(MeasurerDto.from(measurement.getMeasurer())).
                measurer(MeasurerDto.builder().
                    serialNumber(measurer.getSerialNumber()).
                    brand(measurer.getBrand()).
                    model(measurer.getModel()).
                    measurement(measurer.getMeasurement()).
                    build()).
                build();
    }
}