package edu.utn.udee.Udee.dto;

import edu.utn.udee.Udee.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Integer id;
    String username;
    Rol rol;
    ClientDto clientDto;
}
