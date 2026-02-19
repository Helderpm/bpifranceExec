package com.bpifranceexec.exec.domaine.exception;

import lombok.Getter;

/**
 * Exception levée lorsque le pourcentage de détention est invalide.
 * <p>
 * Cette exception fonctionnelle est utilisée pour signaler qu'un pourcentage
 * de détention ne respecte pas les règles métier (doit être entre 0 et 100).
 * Elle fait partie de la couche domaine car elle représente
 * une règle métier indépendante de toute considération technique.
 * </p>
 */
@Getter
public class PourcentageDetentionException extends RuntimeException {
    
    private final int pourcentage;
    
    /**
     * Constructeur de l'exception pourcentage de détention invalide.
     *
     * @param pourcentage Le pourcentage invalide qui a causé l'exception
     */
    public PourcentageDetentionException(int pourcentage) {
        super("Pourcentage de détention invalide: " + pourcentage + "%");
        this.pourcentage = pourcentage;
    }
}
