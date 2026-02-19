package com.bpifranceexec.exec.domaine.model;

/**
 * Représente une entreprise (personne morale) dans le système.
 * <p>
 * Cette classe modélise une entreprise avec ses informations essentielles
 * d'identification. Elle est utilisée comme entité de base pour la gestion
 * des bénéficiaires effectifs.
 * </p>
 * <p>
 * Cette classe suit le pattern Record pour une immutabilité garantie
 * et une réduction du code boilerplate.
 * </p>
 *
 * @param id Identifiant unique de l'entreprise
 * @param nom Nom commercial ou raison sociale de l'entreprise
 * @param siret Numéro SIRET de l'entreprise (14 chiffres obligatoires)
 */
public record Entreprise(
        Long id,
        String nom,
        String siret) {

    /**
     * Vérifie si le numéro SIRET est valide.
     * <p>
     * Un numéro SIRET valide doit contenir exactement 14 chiffres.
     * Cette méthode effectue une validation basique sans vérifier
     * la clé de contrôle Luhn.
     * </p>
     *
     * @return true si le SIRET contient 14 chiffres, false sinon
     */
    public boolean siretValide() {
        return siret != null && siret.matches("\\d{14}");
    }

    /**
     * Vérifie si l'entreprise a des informations complètes.
     * <p>
     * Une entreprise est considérée comme complète si elle a un nom
     * et un numéro SIRET valides.
     * </p>
     *
     * @return true si l'entreprise est complète, false sinon
     */
    public boolean estComplete() {
        return nom != null && !nom.trim().isEmpty() && siretValide();
    }
}
