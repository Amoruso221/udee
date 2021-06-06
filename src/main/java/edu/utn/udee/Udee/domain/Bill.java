package edu.utn.udee.Udee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurer_serial_number", nullable = false)
    private Meter meter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
    private List<Measurement> measurements;

    @Column
    private Double amount; //Measurement * Rate
}
