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
    @Column
    Double totalAmount;
    @Column(columnDefinition = "boolean default false")
    Boolean paid;
}
