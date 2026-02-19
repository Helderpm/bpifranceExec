package com.bpifranceexec.exec.infrastructure.exception;

import lombok.Getter;

/**
 * Exception levée lors d'un problème de connexion à la base de données.
 * <p>
 * Cette exception technique est utilisée pour signaler des problèmes
 * liés à la persistance des données (connexion, transaction, etc.).
 * Elle fait partie de la couche infrastructure car elle représente
 * une contrainte technique indépendante de la logique métier.
 * </p>
 */
@Getter
public class DatabaseConnectionException extends RuntimeException {
    
    private final String operation;
    
    /**
     * Constructeur de l'exception de connexion base de données.
     *
     * @param operation L'opération qui a échoué
     */
    public DatabaseConnectionException(String operation) {
        super("Erreur de connexion base de données lors de: " + operation);
        this.operation = operation;
    }
    
    /**
     * Constructeur de l'exception avec cause.
     *
     * @param operation L'opération qui a échoué
     * @param cause La cause racine de l'exception
     */
    public DatabaseConnectionException(String operation, Throwable cause) {
        super("Erreur de connexion base de données lors de: " + operation, cause);
        this.operation = operation;
    }
}
