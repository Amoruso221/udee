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
    Integer id;



    @Column
    Client client;


    @Column
    Rate rate;



    @Column
    Meter meter;

    @Column
    List<Measurement> measurements;


    @Column
    Double totalAmount;

    @Column(columnDefinition = "boolean default false")
    Boolean paid;

    @Column
    Address address;
}
