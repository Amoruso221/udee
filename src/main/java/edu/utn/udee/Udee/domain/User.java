package edu.utn.udee.Udee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.utn.udee.Udee.domain.enums.RolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name ="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String username;

    @JsonIgnore
    @Column
    String password;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    RolType rolType;
}
