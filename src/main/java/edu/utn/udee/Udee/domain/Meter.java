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
@Table(name = "meters")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialNumber;

    @Column
    private String brand;

    @Column
    private String model;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meter")
    private List<Measurement> measurements;

    @OneToOne
    @JoinColumn(name = "id_address")
    private Address address;
}
