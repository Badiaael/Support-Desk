package com.example.SupportDesk.shared.error;

import java.util.LinkedHashMap; 
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// GlobalExceptionHandler centralise la gestion des erreurs afin d'éviter de repeter le meme code dans chaque controller
@RestControllerAdvice //classe qui écoute les exceptions de tous les controleurs et renvoie du JSON
public class GlobalExceptionHandler {
    @ExceptionHandler (MethodArgumentNotValidException.class) //Cette méthode traite l’exception de type X dans ce cas Lancée automatiquement quand un @Valid échoue On construit details : champ → message.
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) { //cette méthode intercepte les erreurs de validation des données envoyée par l'utilisatuer 
        Map<String, String> details = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> details.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("VALIDATION_ERROR", "invalid informations", details));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity <ApiError> handleUnreadble(HttpMessageNotReadableException ex){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.of("MALFORMED_REQUEST", "Invalid JSON or unreadable request body"));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity <ApiError> handleConflict(ConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError.of("CONFLICT", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError.of("CONFLICT", "L'opération viole une contrainte de la base de données"));
    }
}




// HttpStatus.BAD_REQUEST ->Retourne le code HTTP 400 : la requête contient des données invalides
//ApiError.of(...) : construit un objet contenant le code et le message d'erreur
//ResponseEntity : C'est une classe Spring qui permet de controler la réponse HTTP