package edu.utn.udee.Udee.controller.client;

import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.domain.enums.Rol;
import edu.utn.udee.Udee.dto.UserDto;
import edu.utn.udee.Udee.service.UserService;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.backoffice.ClientService;
import edu.utn.udee.Udee.service.client.ClientBillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Conf.class)
public class ClientBillControllerTest {

    private ClientBillController clientBillController;

    private ClientBillService clientBillService;
    private ClientService clientService;
    private UserService userService;
    private AddressService addressService;
    private ModelMapper modelMapper;
    private Authentication auth;

    @Before
    public void setUp(){
        this.clientBillService = mock(ClientBillService.class);
        this.clientService = mock(ClientService.class);
        this.userService = mock(UserService.class);
        this.addressService = mock(AddressService.class);
        this.modelMapper = mock(ModelMapper.class);
        this.auth = mock(Authentication.class);

        this.clientBillController = new ClientBillController(clientBillService, clientService, userService, addressService, modelMapper);
    }

    @Test
    public void testGetBillsBetweenDatesHttpStatusOk() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Amoruso").rol(Rol.ROLE_CLIENT).client_id(1).build());
        //when(clientBillService.getBillsBetweenDates(startDate, endDate, 1)).thenReturn()
    }


}
