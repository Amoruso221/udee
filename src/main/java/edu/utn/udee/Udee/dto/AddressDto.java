package edu.utn.udee.Udee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.udee.Udee.domain.Address;
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
public class AddressDto {
    Integer id;
    String address;
    String city;
    String state;
    String country;
    ClientDto client;
    RateDto rate;

    public static AddressDto from (Address address) {
        return AddressDto.builder().
                id(address.getId()).
                address(address.getAddress()).
                city(address.getCity()).
                state(address.getState()).
                country(address.getCountry()).
                client(ClientDto.from(address.getClient())).
                rate(RateDto.from(address.getRate())).
                build();
    }
}
