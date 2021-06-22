package edu.utn.udee.Udee.TestUtils;

import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.dto.ClientDto;

public class ClientTestUtils {

    public static ClientDto getClientDto() {
        return ClientDto.builder()
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();
    }

    public static Client getClientWithoutId() {
        return Client.builder()
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();
    }

    public static Client getClientWithId(){
        return Client.builder()
                .id(1)
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();
    }

    public static ClientDto getClientDtoWithId(){
        return ClientDto.builder()
                .id(1)
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();
    }
}
