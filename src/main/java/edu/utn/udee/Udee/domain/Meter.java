package edu.utn.udee.Udee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column
    private Double measurement;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meter")
    private List<Measurement> measurements;

    @OneToOne
    @JoinColumn(name = "id")
    private Address address;


    public List<Measurement> listUnbilledMeasurements (Integer idNewBill) {
        List<Measurement> unbilledMeasurements = this.measurements.stream()
                .filter(x -> x.getIdBill() == 0).collect(Collectors.toList());
        this.measurements.stream().map(x -> x.setidBill(idNewBill));
        return unbilledMeasurements;
    }
}
