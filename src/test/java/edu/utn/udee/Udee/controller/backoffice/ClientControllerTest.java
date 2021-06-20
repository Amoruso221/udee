package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.dto.ClientDto;
import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.service.UserService;
import edu.utn.udee.Udee.service.backoffice.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Conf.class)
public class ClientControllerTest {

    private ClientController clientController;

    private ClientService clientService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        this.clientService = mock(ClientService.class);
        this.userService = mock(UserService.class);
        this.modelMapper = mock(ModelMapper.class);
        this.clientController = new ClientController(clientService, userService, modelMapper);
    }

    @Test
    public void testAddClientOk() throws ClientExistsException {

       ClientDto clientDto = ClientDto.builder()
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();

        Client clientWithoutId = Client.builder()
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();

        Client clientWithId = Client.builder()
                .id(1)
                .name("Matias")
                .surname("Amoruso")
                .dni(1234)
                .address(null)
                .build();

        PowerMockito.mockStatic(Conf.class);
        when(this.clientService.addClient(clientWithoutId)).thenReturn(clientWithId);
        doNothing().when(this.userService).addUser(clientWithId);
        when(Conf.getLocation(clientWithId)).thenReturn(URI.create("http://localhost:8080/api/backoffice/clients/1"));

        ResponseEntity responseEntity = this.clientController.addClient(clientDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(userService, times(1)).addUser(any());
    }

    /*@Test(expected = ClientExistsException.class)
    public void testAddClientException() throws ClientExistsException{

    }*/
}
