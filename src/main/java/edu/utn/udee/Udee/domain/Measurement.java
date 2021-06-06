package edu.utn.udee.Udee.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idMeasurement;

    @Column
    private Double kwh;

    @Column
    //@DateTimeFormat(pattern = "dd.MM.yyyy hh:mm:ss")   //(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurer_serial_number", nullable = false)
    private Measurer measurer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

}
