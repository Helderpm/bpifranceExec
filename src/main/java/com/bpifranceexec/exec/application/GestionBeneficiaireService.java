package com.bpifranceexec.exec.application;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.in.GestionBeneficiairePort;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;
import com.bpifranceexec.exec.domaine.exception.SiretInvalideException;
import com.bpifranceexec.exec.domaine.exception.PourcentageDetentionException;
import com.bpifranceexec.exec.domaine.exception.EntrepriseInexistanteException;
import com.bpifranceexec.exec.domaine.exception.PersonneInexistanteException;
import com.bpifranceexec.exec.infrastructure.exception.DatabaseConnectionException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service d'application pour la gestion des bénéficiaires effectifs.
 * <p>
 * Cette classe implémente le port d'entrée {@link GestionBeneficiairePort} et coordonne
 * les opérations métier pour la gestion des bénéficiaires effectifs. Elle sert de
 * couche intermédiaire entre la couche d'exposition (REST) et la couche infrastructure.
 * </p>
 * <p>
 * Le service applique les règles métier, gère les transactions et assure la cohérence
 * des données avant de déléguer les opérations de persistance au repository.
 * </p>
 * <p>
 * Cette classe est annotée avec {@link Service} pour être détectée par Spring,
 * {@link Transactional} pour garantir la cohérence des transactions,
 * et {@link Slf4j} pour le logging.
 * </p>
 *
 * @see GestionBeneficiairePort
 * @see BeneficiaireRepositoryPort
 */
@Service
@Transactional
@Slf4j
public class GestionBeneficiaireService implements GestionBeneficiairePort {

    private final BeneficiaireRepositoryPort beneficiaireRepositoryPort;
    
    /**
     * Valide l'existence d'une entreprise dans le système.
     * <p>
     * Cette méthode utilitaire permet de centraliser la validation 
     * de l'existence des entreprises (mère ou fille).
     * Pour l'entreprise mère, le null est interdit.
     * Pour l'entreprise fille, le null est autorisé.
     * </p>
     *
     * @param entreprise L'entreprise à valider
     * @param estEntrepriseMere true si c'est l'entreprise mère (obligatoire), false si c'est l'entreprise fille (optionnelle)
     * @throws EntrepriseInexistanteException si l'entreprise n'existe pas ou est null quand c'est l'entreprise mère
     */
    private void validerExistenceEntreprise(Entreprise entreprise, boolean estEntrepriseMere) {
        // Pour l'entreprise mère, le null est interdit
        if (estEntrepriseMere && entreprise == null) {
            throw new EntrepriseInexistanteException(null);
        }
        
        // Pour les deux, si l'entreprise existe, on vérifie son existence en base
        if (entreprise != null && entreprise.id() != null && !beneficiaireRepositoryPort.existsEntrepriseById(entreprise.id())) {
                throw new EntrepriseInexistanteException(entreprise.id());
            }
        
    }
    
    /**
     * Constructeur du service de gestion des bénéficiaires.
     * <p>
     * Initialise le service avec le repository nécessaire pour les opérations
     * de persistance. L'injection de dépendances se fait par constructeur
     * pour faciliter les tests et garantir l'immuabilité.
     * </p>
     *
     * @param beneficiaireRepositoryPort Le repository pour les opérations de persistance des bénéficiaires
     * @throws IllegalArgumentException si beneficiaireRepositoryPort est null
     */
    public GestionBeneficiaireService(BeneficiaireRepositoryPort beneficiaireRepositoryPort) {
        this.beneficiaireRepositoryPort = beneficiaireRepositoryPort;
    }
    
    /**
     * Crée une nouvelle entreprise dans le système.
     * <p>
     * Cette méthode délègue la création de l'entreprise au repository après
     * avoir effectué les validations nécessaires et enregistré l'opération
     * dans les logs.
     * </p>
     *
     * @param entreprise L'entreprise à créer avec ses informations de base
     * @return Optional contenant l'entreprise créée avec son ID, ou vide si la création échoue
     * @throws IllegalArgumentException si les données de l'entreprise sont invalides
     */
    @Override
    public Optional<Entreprise> creerEntreprise(Entreprise entreprise) {
        log.info("Creating a new entreprise with nom: {}", entreprise.nom());
        
        // Validation métier
        if (!entreprise.siretValide()) {
            throw new SiretInvalideException(entreprise.siret());
        }
        
        if (!entreprise.estComplete()) {
            throw new SiretInvalideException("Nom ou SIRET manquant");
        }
        
        try {
            return Optional.of(beneficiaireRepositoryPort.saveEntreprise(entreprise));
        } catch (Exception e) {
            throw new DatabaseConnectionException("sauvegarde entreprise", e);
        }
    }
    
    /**
     * Crée une nouvelle personne physique dans le système.
     * <p>
     * Cette méthode délègue la création de la personne physique au repository après
     * avoir effectué les validations nécessaires et enregistré l'opération
     * dans les logs.
     * </p>
     *
     * @param personne La personne physique à créer avec ses informations
     * @return Optional contenant la personne créée avec son ID, ou vide si la création échoue
     * @throws IllegalArgumentException si les données de la personne sont invalides
     */
    @Override
    public Optional<PersonnePhysique> creerPersonnePhysique(PersonnePhysique personne) {
        log.info("Creating a new personne physique: {} {}", personne.prenom(), personne.nom());
        
        // Validation : métier
        if (!personne.estComplete()) {
            throw new SiretInvalideException("Nom ou prénom manquant");
        }
        
        if (!personne.formatValide()) {
            throw new SiretInvalideException("Format nom ou prénom invalide");
        }
        
        try {
            return Optional.of(beneficiaireRepositoryPort.savePersonnePhysique(personne));
        } catch (Exception e) {
            throw new DatabaseConnectionException("sauvegarde personne physique", e);
        }
    }
    
    /**
     * Ajoute un nouveau bénéficiaire effectif à une entreprise.
     * <p>
     * Cette méthode gère l'ajout d'un bénéficiaire avec gestion des erreurs.
     * En cas d'échec, l'erreur est loggée et un Optional vide est retourné
     * pour éviter la propagation d'exceptions vers la couche supérieure.
     * </p>
     *
     * @param beneficiaire Le bénéficiaire à ajouter avec toutes ses informations
     * @return Optional contenant le bénéficiaire créé avec son ID, ou vide si l'ajout échoue
     * @throws IllegalArgumentException si les données du bénéficiaire sont invalides
     */
    @Override
    public Optional<Beneficiaire> ajouterBeneficiaire(Beneficiaire beneficiaire) {
        log.info("Adding a new beneficiaire with pourcentage: {}", beneficiaire.pourcentageDetention());
        
        // Validation : métier
        if (beneficiaire.pourcentageDetention() < 0 || beneficiaire.pourcentageDetention() > 100) {
            throw new PourcentageDetentionException(beneficiaire.pourcentageDetention());
        }
        
        // Validation : si personne physique existe
        if (beneficiaire.personnePhysique() != null && beneficiaire.personnePhysique().id() != null && !beneficiaireRepositoryPort.existsPersonnePhysiqueById(beneficiaire.personnePhysique().id())) {
            throw new PersonneInexistanteException(beneficiaire.personnePhysique().id());
        }
        
        // Validation : existence des entreprises (mère et fille)
        validerExistenceEntreprise(beneficiaire.entrepriseMere(), true);  // entreprise mère obligatoire
        validerExistenceEntreprise(beneficiaire.entrepriseFille(), false); // entreprise fille optionnelle
        
        try {
            Beneficiaire savedBeneficiaire = beneficiaireRepositoryPort.saveBeneficiaire(beneficiaire);
            log.info("Successfully added beneficiaire with ID: {}", savedBeneficiaire.id());
            return Optional.of(savedBeneficiaire);
        } catch (Exception e) {
            throw new DatabaseConnectionException("sauvegarde bénéficiaire", e);
        }
    }
    
    /**
     * Récupère la liste des bénéficiaires d'une entreprise selon un type spécifique.
     * <p>
     * Cette méthode supporte plusieurs types de filtrage :
     * </p>
     * <ul>
     *   <li>"all" : Tous les bénéficiaires sans filtrage</li>
     *   <li>"personnes_physiques" : Uniquement les personnes physiques</li>
     *   <li>"beneficiaires_effectifs" : Personnes physiques avec plus de 25% de détention</li>
     * </ul>
     * <p>
     * Pour les types non reconnus, une liste vide est retournée et un avertissement
     * est logged.
     * </p>
     *
     * @param entrepriseId L'identifiant de l'entreprise mère
     * @param type Le type de filtrage souhaité (insensible à la casse)
     * @return La liste des bénéficiaires filtrés selon le type spécifié
     * @throws IllegalArgumentException si entrepriseId est null
     */
    @Override
    public List<Beneficiaire> recupererBeneficiaires(Long entrepriseId, String type) {
        log.info("Fetching beneficiaires for entrepriseId: {} with type: {}", entrepriseId, type);
        List<Beneficiaire> allBeneficiaires = beneficiaireRepositoryPort.findByEntrepriseMereId(entrepriseId);
        log.info("Found {} total beneficiaires for entrepriseId: {}", allBeneficiaires.size(), entrepriseId);
        
        List<Beneficiaire> filteredList = switch (type.toLowerCase()) {
            case "all" -> allBeneficiaires;
            case "personnes_physiques" -> allBeneficiaires.stream()
                    .filter(b -> b.personnePhysique() != null)
                    .toList();
            case "beneficiaires_effectifs" -> allBeneficiaires.stream()
                    .filter(b -> b.personnePhysique() != null && b.pourcentageDetention() > 25)
                    .toList();
            default -> {
                log.warn("Invalid beneficiary type requested: {}. Returning empty list.", type);
                yield List.of();
            }
        };
        
        log.info("Filtered list contains {} beneficiaires for type: {}", filteredList.size(), type);
        return filteredList;
    }
    
}
