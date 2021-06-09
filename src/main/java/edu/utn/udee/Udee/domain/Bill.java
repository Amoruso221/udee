package edu.utn.udee.Udee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
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
    private Integer id;
        //***CLIENT***//
    @Column
    private Integer dniClient;
    @Column
    private String fullNameClient;
        //***ADDRESS***//
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;
        //***METER***//
    @Column
    private Integer serialNumberMeter;
        //***MEASUREMENTS***//
    @Column
    private Double initialMeasurement;
    @Column
    private Double finalMeasurement;
    @Column
    private Double totalMeasurement;
    @Column
    private LocalDateTime dateTimeInitialmeasurement;
    @Column
    private LocalDateTime dateTimeFinalmeasurement;
        //***RATE***//
    @Column
    private String descriptionRate;
    @Column
    private Double amountRate;
        //***TOTAL***//
    @Column
    private Double totalAmount; // = totalMeasurement * amountRate



//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "measurer_serial_number", nullable = false)
//    private Meter meter;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
//    private List<Measurement> measurements;
//
//    @Column
//    private Double amount; //Measurement * Rate
}
