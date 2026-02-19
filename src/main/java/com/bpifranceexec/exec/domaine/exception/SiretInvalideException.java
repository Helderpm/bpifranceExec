package com.bpifranceexec.exec.domaine.exception;

import lombok.Getter;

/**
 * Exception levée lorsque le format du numéro SIRET est invalide.
 * <p>
 * Cette exception fonctionnelle est utilisée pour signaler qu'un numéro SIRET
 * ne respecte pas le format attendu (14 chiffres obligatoires).
 * Elle fait partie de la couche domaine car elle représente
 * une règle métier indépendante de toute considération technique.
 * </p>
 */
@Getter
public class SiretInvalideException extends RuntimeException {
    
    private final String siret;
    
    /**
     * Constructeur de l'exception SIRET invalide.
     *
     * @param siret Le numéro SIRET invalide qui a causé l'exception
     */
    public SiretInvalideException(String siret) {
        super("SIRET invalide: " + siret);
        this.siret = siret;
    }
}
