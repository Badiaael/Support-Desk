package com.example.SupportDesk.client;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SupportDesk.client.dto.ClientRequest;
import com.example.SupportDesk.client.dto.ClientResponse;
import com.example.SupportDesk.shared.error.ConflictException;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true) //pour gerer les transactions avec DB
    public List<ClientResponse> findAll(){
        return clientRepository.findAll().stream().map(ClientResponse::from).toList();
    }
    @Transactional
    public ClientResponse create(ClientRequest request){
        String name = request.name().trim();
        String email = request.contactEmail().trim();
        if(clientRepository.existsByNameIgnoreCase(name)){
            throw new ConflictException("a client named : "+ name +" already exists");
        }
        Client saved = clientRepository.save(new Client(name, email));
        return ClientResponse.from(saved);
    }


}
// @service :Elle permet à Spring de reconnaître cette classe comme un composant 
// métier et de créer automatiquement une instance (un Bean).

//clientRepository.findAll()->          .stream()       ->   .map(ClientResponse::from)->    .toList()
//(récupere les clients)          (parcours des clients)      transformation des objets)    (retourne une liste de clientResponse 

// ClientResponse::from : référence de méthode équivalent de c -> ClientResponse.from(c)