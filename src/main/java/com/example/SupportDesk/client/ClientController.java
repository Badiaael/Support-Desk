package com.example.SupportDesk.client;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.example.SupportDesk.client.dto.ClientRequest;
import com.example.SupportDesk.client.dto.ClientResponse;


@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService ;
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @GetMapping
    public List<ClientResponse> list() {
        return clientService.findAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse create(@Valid @RequestBody ClientRequest request) {
        return clientService.create(request);
    }
}
