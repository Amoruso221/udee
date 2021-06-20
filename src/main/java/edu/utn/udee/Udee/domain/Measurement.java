package edu.utn.udee.Udee.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_serial_number", nullable = false)
    private Meter meter;

    @Column//(columnDefinition = "boolean default false")
    private Boolean billed;

}