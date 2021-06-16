package edu.utn.udee.Udee.service.backoffice;

import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.domain.User;
import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Client editClient(Client client, Integer dni) throws ClientNotExistsException {
        if(clientRepository.existsByDni(dni)){
            Client editedClient = clientRepository.getByDni(dni);

//            editedClient.setDni(client.getDni());
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

    public List<Client> allClients(){
        return (List<Client>) clientRepository.findAll();
    }

    public Client findClientById(Integer id) throws ClientNotExistsException {
        return clientRepository.findById(id).orElseThrow(ClientNotExistsException::new);
    }

    public void deleteClientById(Integer id) throws ClientNotExistsException {
        clientRepository.deleteById(id);
    }

    public Client findClientByUser(User user) {
        return clientRepository.findByUser(user);
    }
}
