package com.bpifranceexec.exec.domaine.exception;

import lombok.Getter;

/**
 * Exception levée lorsqu'une personne physique n'existe pas dans le système.
 * <p>
 * Cette exception fonctionnelle est utilisée pour signaler qu'une tentative
 * d'opération sur une personne physique qui n'existe pas a été effectuée.
 * Elle fait partie de la couche domaine car elle représente
 * une règle métier indépendante de toute considération technique.
 * </p>
 */
@Getter
public class PersonneInexistanteException extends RuntimeException {
    
    private final Long personneId;
    
    /**
     * Constructeur de l'exception personne inexistante.
     *
     * @param personneId L'identifiant de la personne inexistante
     */
    public PersonneInexistanteException(Long personneId) {
        super("Personne physique inexistante: " + personneId);
        this.personneId = personneId;
    }
}
