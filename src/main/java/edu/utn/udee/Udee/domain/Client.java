package edu.utn.udee.Udee.domain;

import Intarfaces.URIinterface;
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
@Table(name = "clients")
public class Client implements URIinterface {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @Column
    String name;

    @Column
    String surname;

    @Column(unique=true)
    Integer dni;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    List<Address> address;

    @Override
    public Integer getId() {
        return id;
    }
}
