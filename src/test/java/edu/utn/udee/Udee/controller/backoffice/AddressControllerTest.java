package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.dto.AddressDto;
import edu.utn.udee.Udee.exceptions.AddressExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.exceptions.RateNotExistsException;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static edu.utn.udee.Udee.TestUtils.AddressTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(Conf.class)
public class AddressControllerTest {

    @Mock
    private AddressService addressService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressController addressController = new AddressController(addressService, modelMapper);

    /*@Before
    public  void setUp{

    }*/

    @Test
    public void testAddAddressOk()
            throws AddressExistsException, ClientNotExistsException, RateNotExistsException {
        //BEHAVIORS
        AddressDto addressDtoReceived = getAddressDtoReceived();
        Address addressReceived = getAddressReceived();
        Address addressAdded = getAddressAdded();
        when(modelMapper.map(addressDtoReceived, Address.class)).thenReturn(addressReceived);
//        PowerMockito.mockStatic(Conf.class);
        when(addressService.addAddress(addressReceived)).thenReturn(addressAdded);
        try {
            //EXECUTION
            ResponseEntity responseEntity = addressController.addAddress(getAddressDtoReceived());
            //ASSERTS
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            assertEquals("http://localhost:8080/api/backoffice/addresses/1", responseEntity.getHeaders().getLocation());
        } catch (Exception e) {
            fail("Unexpected Exception.");
        }
    }

}
