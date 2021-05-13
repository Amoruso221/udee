package edu.utn.udee.Udee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "measurers")
public class Measurer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialNumber;

    private String brand;
    private String model;
    private Double measurement;

    //Cliente 1-1

    //Mediciones 1-N

    //Facturas N-1

    //Medidor

}
