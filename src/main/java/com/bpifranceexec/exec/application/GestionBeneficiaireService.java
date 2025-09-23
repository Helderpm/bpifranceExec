package com.bpifranceexec.exec.application;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.in.GestionBeneficiairePort;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class GestionBeneficiaireService implements GestionBeneficiairePort {

    private final BeneficiaireRepositoryPort beneficiaireRepositoryPort;
    
    public GestionBeneficiaireService(BeneficiaireRepositoryPort beneficiaireRepositoryPort) {
        this.beneficiaireRepositoryPort = beneficiaireRepositoryPort;
    }
    
    @Override
    public Optional<Entreprise> creerEntreprise(Entreprise entreprise) {
        log.info("Creating a new entreprise with nom: {}", entreprise.nom());
        return Optional.of(beneficiaireRepositoryPort.saveEntreprise(entreprise));
    }
    
    @Override
    public Optional<PersonnePhysique> creerPersonnePhysique(PersonnePhysique personne) {
        log.info("Creating a new personne physique: {} {}", personne.prenom(), personne.nom());
        return Optional.of(beneficiaireRepositoryPort.savePersonnePhysique(personne));
    }
    
    @Override
    public Optional<Beneficiaire> ajouterBeneficiaire(Beneficiaire beneficiaire) {
        log.info("Adding a new beneficiaire with pourcentage: {}", beneficiaire.pourcentageDetention());
        try {
            Beneficiaire savedBeneficiaire = beneficiaireRepositoryPort.saveBeneficiaire(beneficiaire);
            log.info("Successfully added beneficiaire with ID: {}", savedBeneficiaire.id());
            return Optional.of(savedBeneficiaire);
        } catch (Exception e) {
            log.error("Failed to add beneficiaire. Error: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
    
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