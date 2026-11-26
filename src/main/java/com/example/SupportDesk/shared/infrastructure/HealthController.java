package com.example.SupportDesk.shared.infrastructure;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   //cette classe reçoits des request http et les resultats doit etre convertit enJSON
@RequestMapping("/api")    //sert a assosier une adresse URL comme un routeur qui indique à le serveur quelle fonction exécuter lorsqu'un utilisateur ou une application appelle l'URL

public class HealthController {
    @GetMapping("/health")                    //repond aux requêtes GET sur /api/health
    public Map <String , String> health(){
        return Map.of("Status","UP");     // immutable map avec (cle , val)
    }
}
