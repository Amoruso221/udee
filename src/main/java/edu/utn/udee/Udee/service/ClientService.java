package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client client) throws ClientExistsException {
        if (!clientRepository.existsByDni(client.getDni())){
            return clientRepository.save(client);
        } else {
            throw new ClientExistsException();
        }
    }

    public Client editClient(Client client) throws ClientNotExistsException {
        if(clientRepository.existsByDni(client.getDni())){
            Client editedClient = clientRepository.getByDni(client.getDni());

            editedClient.setDni(client.getDni());
            editedClient.setName(client.getName());
            editedClient.setSurname(client.getSurname());

            clientRepository.save(editedClient);

            return editedClient;
        } else {
            throw new ClientNotExistsException();
        }
    }

    public Page<Client> allClients(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public Client findClientById(Integer id) throws ClientNotExistsException {
        return clientRepository.findById(id).orElseThrow(ClientNotExistsException::new);
    }

    public void deleteClientById(Integer id) throws ClientNotExistsException {
        clientRepository.deleteById(id);
    }

}
