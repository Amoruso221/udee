package edu.utn.udee.Udee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @Column
    String address;

    @Column
    String city;

    @Column
    String state;

    @Column
    String country;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client", nullable = false)
    Client client;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rate", nullable = false)
    Rate rate;
<<<<<<< HEAD

    @Column
    private List<Bill> bills;
=======
>>>>>>> e5525dd85fb81ad70e8fcc5c531044b66b782b25
}
