package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.backoffice.BillService;
import edu.utn.udee.Udee.service.backoffice.ClientService;
import edu.utn.udee.Udee.service.backoffice.MeterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Conf.class)
public class BillControllerTest {

    @Mock
    private BillService billService;
    @Mock
    private MeterService meterService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AddressService addressService;
    @Mock
    private ClientService clientService;

    @InjectMocks
    private BillController billController;

    @Test
    public void testCreateAllBillsOk()
            throws MeterNotExistsException {

    }
}
