package com.bpifranceexec.exec.domaine.model;

/**
 * Représente une personne physique dans le système.
 * <p>
 * Cette classe modélise une personne physique avec ses informations
 * d'identité de base. Elle est utilisée pour représenter les bénéficiaires
 * effectifs qui sont des personnes physiques.
 * </p>
 * <p>
 * Cette classe suit le pattern Record pour une immutabilité garantie
 * et une réduction du code boilerplate.
 * </p>
 *
 * @param id Identifiant unique de la personne physique
 * @param nom Nom de famille de la personne
 * @param prenom Prénom de la personne
 */
public record PersonnePhysique(
        Long id,
        String nom,
        String prenom) {

    /**
     * Retourne le nom complet de la personne physique.
     * <p>
     * Le nom complet est formaté comme "PRÉNOM NOM" avec les espaces
     * appropriés et la gestion des valeurs nulles.
     * </p>
     *
     * @return Le nom complet formaté, ou une chaîne vide si les deux champs sont nuls
     */
    public String nomComplet() {
        if (prenom == null && nom == null) {
            return "";
        }
        if (prenom == null) {
            return nom.trim();
        }
        if (nom == null) {
            return prenom.trim();
        }
        return (prenom.trim() + " " + nom.trim()).trim();
    }

    /**
     * Vérifie si la personne physique a des informations complètes.
     * <p>
     * Une personne est considérée comme complète si elle a un nom
     * et un prénom non nuls et non vides.
     * </p>
     *
     * @return true si la personne est complète, false sinon
     */
    public boolean estComplete() {
        return nom != null && !nom.trim().isEmpty() 
            && prenom != null && !prenom.trim().isEmpty();
    }

    /**
     * Vérifie si le nom ou le prénom contiennent uniquement des caractères alphabétiques.
     * <p>
     * Cette validation basique vérifie que les champs ne contiennent que des lettres,
     * des espaces, des tirets et des apostrophes (caractères courants dans les noms).
     * </p>
     *
     * @return true si le format est valide, false sinon
     */
    public boolean formatValide() {
        String nomPattern = "^[a-zA-Z\\s\\-']+$";
        return (nom == null || nom.matches(nomPattern)) 
            && (prenom == null || prenom.matches(nomPattern));
    }
}
