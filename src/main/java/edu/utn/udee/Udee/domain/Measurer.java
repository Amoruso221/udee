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
@Table(name = "measurers")
public class Measurer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialNumber;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private Double measurement;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurer")
    private List<Measurement> measurements;

    @OneToOne
    @JoinColumn(name = "id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurer")
    private List<Bill> bills;

}
