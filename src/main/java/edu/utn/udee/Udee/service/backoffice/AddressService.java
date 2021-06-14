package edu.utn.udee.Udee.service.backoffice;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.exceptions.AddressExistsException;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteAddressById(Integer id) throws AddressNotExistsException {
        addressRepository.deleteById(id);
    }

    public Address editAddress(Address address, Integer id) throws AddressNotExistsException {
            Address editedAddress = addressRepository.findById(id).orElseThrow(AddressNotExistsException::new);

            editedAddress.setCity(address.getCity());
            editedAddress.setClient(address.getClient());
            editedAddress.setCountry(address.getCountry());
            editedAddress.setAddress(address.getAddress());
            editedAddress.setRate(address.getRate());
            editedAddress.setState(address.getState());

            addressRepository.save(editedAddress);

            return editedAddress;
    }

    public Address findAddressById(Integer id) throws AddressNotExistsException {
        return addressRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Address findAddressByAddress(String address){
        return addressRepository.findAddressByAddress(address);
    }

    public List<Address> getAddressByClientId(Integer id){
        return addressRepository.findAddressByClientId(id);
    }

    /*public Address editAddress(Address address) throws AddressNotExistsException {
        if(addressRepository.)
    }*/
}
