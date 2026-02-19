package com.bpifranceexec.exec.domaine.model;

/**
 * Représente un bénéficiaire effectif d'une entreprise.
 * <p>
 * Un bénéficiaire effectif est défini comme une personne physique ou morale
 * qui détient directement ou indirectement plus de 25% du capital social
 * d'une entreprise.
 * </p>
 * <p>
 * Cette classe suit le pattern Record pour une immutabilité garantie
 * et une réduction du code boilerplate.
 * </p>
 *
 * @param id Identifiant unique du bénéficiaire
 * @param entrepriseMere L'entreprise dans laquelle le bénéficiaire détient des parts
 * @param personnePhysique La personne physique bénéficiaire (si applicable)
 * @param entrepriseFille L'entreprise morale bénéficiaire (si applicable)
 * @param pourcentageDetention Pourcentage de détention dans l'entreprise mère (0-100)
 */
public record Beneficiaire(
        Long id,
        Entreprise entrepriseMere,
        PersonnePhysique personnePhysique,
        Entreprise entrepriseFille,
        int pourcentageDetention) {

    /**
     * Vérifie si ce bénéficiaire est effectif selon la définition légale.
     * <p>
     * Un bénéficiaire est considéré comme effectif s'il détient plus de 25%
     * du capital de l'entreprise mère.
     * </p>
     *
     * @return true si le pourcentage de détention est supérieur à 25%, false sinon
     */
    public boolean estEffectif() {
        return pourcentageDetention > 25;
    }

    /**
     * Vérifie si le bénéficiaire est une personne physique.
     *
     * @return true si le bénéficiaire est une personne physique, false si c'est une personne morale
     */
    public boolean estPersonnePhysique() {
        return personnePhysique != null;
    }

    /**
     * Vérifie si le bénéficiaire est une personne morale (entreprise).
     *
     * @return true si le bénéficiaire est une entreprise, false si c'est une personne physique
     */
    public boolean estPersonneMorale() {
        return entrepriseFille != null;
    }
}
