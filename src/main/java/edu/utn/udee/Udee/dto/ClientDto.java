package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
    Integer id;
    String name;
    String surname;
    Integer dni;
    List<Address> address;

    public static ClientDto from (Client client) {
        return ClientDto.builder().
                id(client.getId()).
                name(client.getName()).
                surname(client.getSurname()).
                dni(client.getDni()).
                build();
    }
}
