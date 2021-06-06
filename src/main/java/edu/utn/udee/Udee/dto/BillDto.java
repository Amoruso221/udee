package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Bill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {

    private Integer id;
    private MeterDto measurer;
    private List<MeasurementDto> measurements;
    private Double amount;

    public static BillDto from(Bill bill){
        return BillDto.builder().
                id(bill.getId()).
                measurer(MeterDto.from(bill.getMeter())).
                //Measurements?
                amount(bill.getAmount()).
                build();
    }

}
