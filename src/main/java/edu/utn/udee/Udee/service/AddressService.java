package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.exceptions.AddressExistsException;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) throws AddressExistsException {
        if(!addressRepository.existsByAddress(address.getAddress())){
            return addressRepository.save(address);
        } else {
            throw new AddressExistsException();
        }
    }

    public Page<Address> allAddress(Pageable pageable){
        return addressRepository.findAll(pageable);
    }

    public Address findAddressById(Integer id) throws AddressNotExistsException {
        return addressRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public void deleteAddressById(Integer id) throws AddressNotExistsException {
        addressRepository.deleteById(id);
    }
}
