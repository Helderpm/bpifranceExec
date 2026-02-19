package com.bpifranceexec.exec.domaine.port.in;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;

import java.util.List;
import java.util.Optional;

/**
 * Port d'entrée pour la gestion des bénéficiaires effectifs.
 * <p>
 * Cette interface définit les cas d'utilisation (use cases) pour la gestion
 * des bénéficiaires effectifs dans le système. Elle fait partie de la couche
 * d'application et implémente le principe d'inversion des dépendances
 * de l'architecture hexagonale.
 * </p>
 * <p>
 * Les implémentations de cette port sont responsables de la coordination
 * des opérations métier et de la validation des règles fonctionnelles.
 * </p>
 */
public interface GestionBeneficiairePort {
    
    /**
     * Crée une nouvelle entreprise dans le système.
     * <p>
     * Cette méthode permet d'ajouter une nouvelle entreprise qui pourra
     * ensuite avoir des bénéficiaires effectifs. Des validations sont
     * effectuées sur les données de l'entreprise (SIRET, nom, etc.).
     * </p>
     *
     * @param entreprise L'entreprise à créer avec ses informations de base
     * @return Optional contenant l'entreprise créée avec son ID, ou vide si la création échoue
     * @throws IllegalArgumentException si les données de l'entreprise sont invalides
     */
    Optional<Entreprise> creerEntreprise(Entreprise entreprise);
    
    /**
     * Crée une nouvelle personne physique dans le système.
     * <p>
     * Cette méthode permet d'ajouter une personne physique qui pourra
     * être désignée comme bénéficiaire effectif. Des validations sont
     * effectuées sur les données de la personne (nom, prénom).
     * </p>
     *
     * @param personne La personne physique à créer avec ses informations
     * @return Optional contenant la personne créée avec son ID, ou vide si la création échoue
     * @throws IllegalArgumentException si les données de la personne sont invalides
     */
    Optional<PersonnePhysique> creerPersonnePhysique(PersonnePhysique personne);
    
    /**
     * Ajoute un nouveau bénéficiaire effectif à une entreprise.
     * <p>
     * Cette méthode permet d'associer un bénéficiaire (personne physique ou morale)
     * à une entreprise mère avec un pourcentage de détention spécifié.
     * Le système vérifie que le bénéficiaire respecte les critères
     * de définition d'un bénéficiaire effectif.
     * </p>
     *
     * @param beneficiaire Le bénéficiaire à ajouter avec toutes ses informations
     * @return Optional contenant le bénéficiaire créé avec son ID, ou vide si l'ajout échoue
     * @throws IllegalArgumentException si les données du bénéficiaire sont invalides
     * @throws IllegalStateException si l'entreprise mère n'existe pas
     */
    Optional<Beneficiaire> ajouterBeneficiaire(Beneficiaire beneficiaire);
    
    /**
     * Récupère la liste des bénéficiaires d'une entreprise selon un type spécifique.
     * <p>
     * Cette méthode permet de filtrer les bénéficiaires selon leur type
     * (personne physique, personne morale, ou tous les types).
     * </p>
     *
     * @param entrepriseId L'identifiant de l'entreprise mère
     * @param type Le type de bénéficiaires à récupérer :
     *            "PHYSIQUE" pour les personnes physiques uniquement,
     *            "MORALE" pour les personnes morales uniquement,
     *            "TOUS" ou null pour tous les bénéficiaires
     * @return La liste des bénéficiaires correspondant aux critères, jamais null
     * @throws IllegalArgumentException si l'entrepriseId est null
     * @throws IllegalStateException si l'entreprise n'existe pas
     */
    List<Beneficiaire> recupererBeneficiaires(Long entrepriseId, String type);

}
