package edu.utn.udee.Udee.service.client;

import edu.utn.udee.Udee.TestUtils.AddressTestUtils;
import edu.utn.udee.Udee.TestUtils.MeasurementTestUtils;
import edu.utn.udee.Udee.TestUtils.MeterTestUtils;
import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.repository.ClientMeasurementRepository;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.backoffice.MeasurementService;
import edu.utn.udee.Udee.service.backoffice.MeterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Conf.class)
public class ClientMeasurementServiceTest {

    private ClientMeasurementService clientMeasurementService;

    private ClientMeasurementRepository clientMeasurementRepository;
    private AddressService addressService;
    private MeterService meterService;
    private MeasurementService measurementService;

    @Before
    public void setUp(){
        this.clientMeasurementRepository = mock(ClientMeasurementRepository.class);
        this.addressService = mock(AddressService.class);
        this.meterService = mock(MeterService.class);
        this.measurementService = mock(MeasurementService.class);

        this.clientMeasurementService = new ClientMeasurementService(clientMeasurementRepository, addressService, meterService, measurementService);
    }

    /*@Test
    public void testGetTotalKwhAndAmountBetweenDatesOk() throws AddressNotExistsException, MeterNotExistsException  {
        when(addressService.findAddressByClientId(any())).thenReturn(AddressTestUtils.getAddressList());
        when(meterService.getByAddress(any())).thenReturn(MeterTestUtils.get);

    }*/

    @Test
    public void testGetBetweenDatesOk() {
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        when(clientMeasurementRepository.findBetweenDates(1, begin, end)).thenReturn(MeasurementTestUtils.getMeasurementUnbilledList());
        List<Measurement> measurementList = clientMeasurementService.getBetweenDates(1, begin, end);
        assertEquals(MeasurementTestUtils.getMeasurementUnbilledList(), measurementList);
    }
}
