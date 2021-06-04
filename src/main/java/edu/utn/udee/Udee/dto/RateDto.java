package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Rate;
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
public class RateDto {

    private Integer id;
    private String description;
    private Double amount;
    private List<AddressDto> addresses;

    public static RateDto from(Rate rate){
        return RateDto.builder().
                id(rate.getId()).
                description(rate.getDescription()).
                amount(rate.getAmount()).
                //addresses
                build();
    }

}
