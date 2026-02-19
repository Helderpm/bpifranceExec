package com.bpifranceexec.exec.domaine.exception;

import lombok.Getter;

/**
 * Exception levée lorsqu'une entreprise n'existe pas dans le système.
 * <p>
 * Cette exception fonctionnelle est utilisée pour signaler qu'une tentative
 * d'opération sur une entreprise qui n'existe pas a été effectuée.
 * Elle fait partie de la couche domaine car elle représente
 * une règle métier indépendante de toute considération technique.
 * </p>
 */
@Getter
public class EntrepriseInexistanteException extends RuntimeException {
    
    private final Long entrepriseId;
    
    /**
     * Constructeur de l'exception entreprise inexistante.
     *
     * @param entrepriseId L'identifiant de l'entreprise inexistante
     */
    public EntrepriseInexistanteException(Long entrepriseId) {
        super("Entreprise inexistante: " + entrepriseId);
        this.entrepriseId = entrepriseId;
    }
}
