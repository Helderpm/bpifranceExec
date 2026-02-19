package com.bpifranceexec.exec.infrastructure.exception;

import lombok.Getter;

/**
 * Exception levée lors d'un échec d'opération de repository.
 * <p>
 * Cette exception technique est utilisée pour signaler des problèmes
 * lors des opérations de persistance (sauvegarde, recherche, etc.).
 * Elle fait partie de la couche infrastructure car elle représente
 * une contrainte technique indépendante de la logique métier.
 * </p>
 */
@Getter
public class RepositoryOperationFailedException extends RuntimeException {
    
    private final String operation;
    private final String entityType;
    
    /**
     * Constructeur de l'exception d'opération repository échouée.
     *
     * @param operation Le type d'opération (save, find, delete, etc.)
     * @param entityType Le type d'entité concernée
     */
    public RepositoryOperationFailedException(String operation, String entityType) {
        super("Opération '" + operation + "' échouée pour l'entité: " + entityType);
        this.operation = operation;
        this.entityType = entityType;
    }
    
    /**
     * Constructeur avec cause.
     *
     * @param operation Le type d'opération
     * @param entityType Le type d'entité concernée
     * @param cause La cause racine
     */
    public RepositoryOperationFailedException(String operation, String entityType, Throwable cause) {
        super("Opération '" + operation + "' échouée pour l'entité: " + entityType, cause);
        this.operation = operation;
        this.entityType = entityType;
    }
}
