package edu.utn.udee.Udee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    Integer dni;

    @Column
    LocalDate date;

    @Column
    String fullName;

    @Column
    String address;

    @Column
    String city;

    @Column
    Integer meterSerialNumber;

    @Column
    Double firstMeasurement;

    @Column
    Double lastMeasurement;

    @Column
    LocalDateTime firstMeasurementDateTime;

    @Column
    LocalDateTime lastMeasurementDateTime;

    @Column
    Double totalMeasurementKwh;

    @Column
    String rate; //rate type

    @Column
    Double totalAmount;

    @Column(columnDefinition = "boolean default false")
    Boolean paid;
}
