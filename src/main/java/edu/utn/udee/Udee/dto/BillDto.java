package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Bill;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Measurer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {

    private Integer id;
    private MeasurerDto measurer;
    private List<MeasurementDto> measurements;
    private Double amount;

    public static BillDto from(Bill bill){
        return BillDto.builder().
                id(bill.getId()).
                measurer(MeasurerDto.from(bill.getMeasurer())).
                //Measurements?
                amount(bill.getAmount()).
                build();
    }

}
