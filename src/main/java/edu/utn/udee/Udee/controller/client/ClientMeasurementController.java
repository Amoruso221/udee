package edu.utn.udee.Udee.controller.client;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.dto.UserDto;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.client.ClientMeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/client/measurements")
public class ClientMeasurementController {

    private final ClientMeasurementService clientMeasurementService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientMeasurementController(ClientMeasurementService clientMeasurementService, ModelMapper modelMapper, AddressService addressService){
        this.clientMeasurementService = clientMeasurementService;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity totalKwhAndMoneyBetweenDates(@PathVariable(value = "start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, @PathVariable(value = "end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate, Authentication auth){
        UserDto userDto = (UserDto) auth.getPrincipal();
        List<Address> addressList = addressService.getAddressByClientId(userDto.getClient_id());
        //falta terminar
    }


}
