package edu.utn.udee.Udee.TestUtils;

import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.dto.ClientDto;

import java.util.Collections;
import java.util.List;

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

<<<<<<< HEAD
    public static ClientDto getClientDtoWithId(){
        return ClientDto.builder()
                .id(1)
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();
    }
=======
    public static List<Client> getClientsList(){
        List<Client> clientList = List.of(Client.builder().name("Matias").surname("Amoruso").dni(1234).address(null).build(),
                Client.builder().name("Federico").surname("Mendez").dni(1234).address(null).build()
        );

        return clientList;
    }

    public static List<Client> getEmtyClientList(){
        return Collections.emptyList();
    }

    public static List<ClientDto> getClientsDtoList(){
        List<ClientDto> clientDtoList = List.of(ClientDto.builder().name("Matias").surname("Amoruso").dni(1234).address(null).build(),
                ClientDto.builder().name("Federico").surname("Mendez").dni(1234).address(null).build()
        );

        return clientDtoList;
    }

>>>>>>> 04ec530bd2da5e8d5b50e9d9604cac6f89dcd908
}
