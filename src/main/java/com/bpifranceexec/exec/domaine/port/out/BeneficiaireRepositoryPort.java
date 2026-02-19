package com.bpifranceexec.exec.domaine.port.out;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;

import java.util.List;

/**
 * Port de sortie pour la persistance des données des bénéficiaires.
 * <p>
 * Cette interface définit les contrats d'accès aux données pour les entités
 * du domaine (bénéficiaires, entreprises, personnes physiques). Elle fait partie
 * de la couche infrastructure et implémente le principe d'inversion des
 * dépendances de l'architecture hexagonale.
 * </p>
 * <p>
 * Les implémentations de cette port sont responsables de l'interaction
 * avec le système de persistance (base de données, fichiers, API externes, etc.).
 * </p>
 */


public interface BeneficiaireRepositoryPort {
    
    /**
     * Recherche tous les bénéficiaires d'une entreprise mère spécifique.
     * <p>
     * Cette méthode retourne la liste complète des bénéficiaires effectifs
     * (personnes physiques et morales) qui détiennent des parts dans
     * l'entreprise spécifiée.
     * </p>
     *
     * @param entrepriseMereId L'identifiant de l'entreprise mère pour laquelle
     *                         rechercher les bénéficiaires
     * @return La liste des bénéficiaires de l'entreprise, jamais null.
     *         Retourne une liste vide si aucun bénéficiaire n'est trouvé
     * @throws IllegalArgumentException si entrepriseMereId est null
     */
    List<Beneficiaire> findByEntrepriseMereId(Long entrepriseMereId);
    
    /**
     * Sauvegarde un bénéficiaire effectif dans le système de persistance.
     * <p>
     * Cette méthode crée ou met à jour un bénéficiaire selon que
     * l'identifiant soit null ou non. Elle gère également les relations
     * avec l'entreprise mère et le bénéficiaire (personne ou entreprise).
     * </p>
     *
     * @param beneficiaire Le bénéficiaire à sauvegarder avec toutes ses informations
     * @return Le bénéficiaire sauvegardé avec son identifiant généré ou mis à jour
     * @throws IllegalArgumentException si beneficiaire ou ses données obligatoires sont null
     * @throws IllegalStateException si une contrainte de base de données est violée
     */
    Beneficiaire saveBeneficiaire(Beneficiaire beneficiaire);

    /**
     * Sauvegarde une entreprise dans le système de persistance.
     * <p>
     * Cette méthode crée ou met à jour une entreprise selon que
     * l'identifiant soit null ou non. Des validations sur le format
     * du SIRET peuvent être effectuées au niveau de la persistance.
     * </p>
     *
     * @param entreprise L'entreprise à sauvegarder avec ses informations
     * @return L'entreprise sauvegardée avec son identifiant généré ou mis à jour
     * @throws IllegalArgumentException si entreprise ou ses données obligatoires sont null
     * @throws IllegalStateException si une contrainte d'unicité (SIRET) est violée
     */
    Entreprise saveEntreprise(Entreprise entreprise);

    /**
     * Sauvegarde une personne physique dans le système de persistance.
     * <p>
     * Cette méthode crée ou met à jour une personne physique selon que
     * l'identifiant soit null ou non. Elle assure la cohérence des données
     * personnelles (nom, prénom).
     * </p>
     *
     * @param personnePhysique La personne physique à sauvegarder
     * @return La personne physique sauvegardée avec son identifiant généré ou mis à jour
     * @throws IllegalArgumentException si personnePhysique ou ses données obligatoires sont null
     * @throws IllegalStateException si une contrainte de base de données est violée
     */
    PersonnePhysique savePersonnePhysique(PersonnePhysique personnePhysique);
    
    /**
     * Vérifie si une personne physique existe dans le système de persistance.
     * <p>
     * Cette méthode permet de valider l'existence d'une personne physique
     * avant d'effectuer des opérations qui dépendent de sa présence.
     * </p>
     *
     * @param personneId L'identifiant de la personne physique à vérifier
     * @return true si la personne physique existe, false sinon
     * @throws IllegalArgumentException si personneId est null
     */
    boolean existsPersonnePhysiqueById(Long personneId);
    
    /**
     * Vérifie si une entreprise existe dans le système de persistance.
     * <p>
     * Cette méthode permet de valider l'existence d'une entreprise
     * avant d'effectuer des opérations qui dépendent de sa présence.
     * </p>
     *
     * @param entrepriseId L'identifiant de l'entreprise à vérifier
     * @return true si l'entreprise existe, false sinon
     * @throws IllegalArgumentException si entrepriseId est null
     */
    boolean existsEntrepriseById(Long entrepriseId);
    
}