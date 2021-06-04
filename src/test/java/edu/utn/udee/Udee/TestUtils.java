package edu.utn.udee.Udee;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Measurer;

import java.util.Collections;
import java.util.List;

public class TestUtils {

    public static Measurer createMeasurerWithBrandAndModel(){
        Measurer m = new Measurer();
        m.setBrand("Philips");
        m.setModel("Deluxe");
        return m;
    }

    public static Measurer createNewMeasurer(){
        List<Measurement> measurementsEmptyList = Collections.emptyList();
        Measurer m = Measurer.builder().
                serialNumber(1).
                brand("Philips").
                model("Deluxe").
                measurement(0.0).
                measurements(measurementsEmptyList).
                address(null).
                build();
        return m;
    }
}