package edu.utn.udee.Udee.TestUtils;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.dto.MeterDto;

import java.util.Collections;
import java.util.List;

import static edu.utn.udee.Udee.TestUtils.AddressTestUtils.*;

public class MeterTestUtils {

    public static MeterDto getMeterDtoReceived (){
        return MeterDto.builder().
                brand("TestBrand").
                model("TestModel").
                measurements(null).
                address(getAddressDtoReceived()).
                build();
    }

    public static Meter getMeterReceived (){
        return Meter.builder().
                serialNumber(1).
                brand("TestBrand").
                model("TestModel").
                measurements(null).
                address(getAddressReceived()).
                build();
    }

    public static Meter getMeterAdded (){
        return Meter.builder().
                serialNumber(1).
                brand("TestBrand").
                model("TestModel").
                measurements(Collections.emptyList()).
                address(getAddressAdded()).
                build();
    }

    public static MeterDto getMeterDtoAdded (){
        return MeterDto.from(getMeterAdded());
    }

    public static List<Meter> getMeterList(){
        List<Address> addresses = getAddressList();
        return List.of(
                Meter.builder().
                        serialNumber(1).
                        brand("TestBrand").
                        model("TestModel").
                        measurements(Collections.emptyList()).
                        address(addresses.get(0)).
                        build(),
                Meter.builder().
                        serialNumber(2).
                        brand("TestBrandTwo").
                        model("TestModelTwo").
                        measurements(Collections.emptyList()).
                        address(addresses.get(1)).
                        build());
    }

}
