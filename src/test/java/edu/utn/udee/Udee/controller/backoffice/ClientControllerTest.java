package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.TestUtils.ClientTestUtils;
import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.dto.ClientDto;
import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.service.UserService;
import edu.utn.udee.Udee.service.backoffice.ClientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

        ClientDto clientDto = ClientTestUtils.getClientDto();
        Client clientWithoutId = ClientTestUtils.getClientWithoutId();
        Client clientWithId =ClientTestUtils.getClientWithId();

        PowerMockito.mockStatic(Conf.class);
        when(clientService.addClient(clientWithoutId)).thenReturn(clientWithId);
        doNothing().when(userService).addUser(clientWithId);

        try {
            ResponseEntity responseEntity = clientController.addClient(clientDto);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            verify(userService, times(1)).addUser(any());
        } catch (ClientExistsException e) {
            Assert.fail("This test shouldn't throw an exception");
        }
    }

    @Test
    public void testAddClientException() throws ClientExistsException {
        when(clientService.addClient(any())).thenThrow(new ClientExistsException());

        try {
            ResponseEntity responseEntity = clientController.addClient(ClientTestUtils.getClientDto());
            Assert.fail("It should throw ClientExistException");
        } catch (ClientExistsException e) {
            assertThat(e, instanceOf(ClientExistsException.class));
        }
    }

    @Test
    public void testEditClientOk() throws ClientNotExistsException {
        when(clientService.editClient(ClientTestUtils.getClientWithoutId(), 1234)).thenReturn(ClientTestUtils.getClientWithoutId());

        ResponseEntity responseEntity = clientController.editClient(ClientTestUtils.getClientDto(), 1234);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testEditClientException() throws ClientNotExistsException {
        when(clientService.editClient(any(),any())).thenThrow(new ClientNotExistsException());

        try {
            ResponseEntity responseEntity = clientController.editClient(ClientTestUtils.getClientDto(), 9999);
            Assert.fail("It should throw ClientNotExistException");
        } catch (ClientNotExistsException e) {
            assertThat(e, instanceOf(ClientNotExistsException.class));
        }
    }

}
