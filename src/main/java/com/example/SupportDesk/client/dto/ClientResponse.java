package com.example.SupportDesk.client.dto;

import java.time.Instant;
import com.example.SupportDesk.client.Client;

public record ClientResponse(Long id, String name, String contactEmail, Instant createdAt) {

    public static ClientResponse from(Client client) { //Convertit l’entite en reponse
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getContactEmail(),
                client.getCreatedAt());
    }
}