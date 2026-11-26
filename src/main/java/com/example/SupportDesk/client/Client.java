package com.example.SupportDesk.client;

import jakarta.persistence.Entity;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity                                // Une entité est une classe Java qui représente une ligne de table
@Table(name = "client")              //le nom du table sql

public class Client {
    @Id                  //cle primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;

    @Column(nullable = false, unique = true , length = 150)    //@column relie l'attribut a la colonne 
    private String name;

    @Column(name = "contact_email", nullable = false , length = 255)   
    private String contactEmail;

    @Column(name = "created_at", nullable = false , updatable  = false)
    private Instant createdAt = Instant.now(); //Instant type java pour un moment précis 

    protected Client(){
        //requis par JAP
    }
//constructeur avec parametre
    public Client(String name, String contactEmail){
        this.name = name;
        this.contactEmail = contactEmail;
    }
//getters
    public Long getId(){return id;}
    public String getName(){return name;}
    public String getContactEmail(){return contactEmail;}
    public Instant getCreatedAt(){return createdAt ;}
}
