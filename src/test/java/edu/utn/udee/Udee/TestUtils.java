package edu.utn.udee.Udee;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Meter;

import java.util.Collections;
import java.util.List;

public class TestUtils {

    public static Meter createMeasurerWithBrandAndModel(){
        Meter m = new Meter();
        m.setBrand("Philips");
        m.setModel("Deluxe");
        return m;
    }

    public static Meter createNewMeasurer(){
        List<Measurement> measurementsEmptyList = Collections.emptyList();
        Meter m = Meter.builder().
                serialNumber(1).
                brand("Philips").
                model("Deluxe").
                measurements(measurementsEmptyList).
                address(null).
                build();
        return m;
    }
}