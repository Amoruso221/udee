package edu.utn.udee.Udee.dto;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.domain.User;
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
    //ClientDto clientDto;

    public static  UserDto from (User user) {
        return UserDto.builder().
                id(user.getId()).
                username(user.getUsername()).
                rol(user.getRol()).
                //clientDto(ClientDto.from(user.getClient())).
                build();
    }
}