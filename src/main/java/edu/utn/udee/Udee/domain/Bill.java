package edu.utn.udee.Udee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
<<<<<<< HEAD
    @Column
    Client client;
    @Column
    Address address;
    @Column
    Meter meter;
    @Column
    List<Measurement> measurements;
    @Column
    Rate rate;
=======

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

>>>>>>> 18d787cff3e4afbb8dfd4a58f83fd27427922923
    @Column
    Double totalAmount;
    @Column(columnDefinition = "boolean default false")
    Boolean paid;
}
