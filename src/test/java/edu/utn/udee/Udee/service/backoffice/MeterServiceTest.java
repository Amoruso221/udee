package edu.utn.udee.Udee.service.backoffice;

import edu.utn.udee.Udee.TestUtils.AddressTestUtils;
import edu.utn.udee.Udee.TestUtils.MeterTestUtils;
import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.AddressWithMeterException;
import edu.utn.udee.Udee.repository.MeterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static edu.utn.udee.Udee.TestUtils.AddressTestUtils.*;
import static edu.utn.udee.Udee.TestUtils.MeterTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Conf.class)
public class MeterServiceTest {

    @Mock
    private MeterRepository meterRepository;
    @Mock
    private MeasurementService measurementService;
    @Mock
    private AddressService addressService;

    @InjectMocks
    private MeterService meterService;

    @Test
    public void testAddMeterOk()
            throws AddressNotExistsException, AddressWithMeterException {
        when(addressService.findAddressById(anyInt())).thenReturn(getAddressAdded());
        when(meterRepository.findByAddress(any())).thenReturn(null);
        when(meterRepository.save(any())).thenReturn(getMeterAdded());
        try{
            Meter newMeter = meterService.addMeter(getMeterReceived());
            assertEquals(getMeterAdded(), newMeter);
        } catch (Exception ex){
            fail("Unexpected Exception!");
        }

    }
}
