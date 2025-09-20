package com.bpifranceexec.exec.domaine.application;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.in.GestionBeneficiairePort;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeneficiaireService implements GestionBeneficiairePort {

    private final BeneficiaireRepositoryPort beneficiaireRepositoryPort;

    @Override
    public Optional<Entreprise> creerEntreprise(Entreprise entreprise) {
        return Optional.of(beneficiaireRepositoryPort.saveEntreprise(entreprise));
    }
    
    @Override
    public Optional<PersonnePhysique> creerPersonnePhysique(PersonnePhysique personne) {
        return Optional.of(beneficiaireRepositoryPort.savePersonnePhysique(personne));
    }
    
    @Override
    public Optional<Beneficiaire> ajouterBeneficiaire(Beneficiaire beneficiaire) {
        return Optional.of(beneficiaireRepositoryPort.saveBeneficiaire(beneficiaire));
    }
    
    @Override
    public List<Beneficiaire> recupererBeneficiaires(Long entrepriseId, String type) {
        List<Beneficiaire> allBeneficiaires = beneficiaireRepositoryPort.findByEntrepriseMereId(entrepriseId);
        
        return switch (type.toLowerCase()) {
            case "all" -> allBeneficiaires;
            case "personnes_physiques" -> allBeneficiaires.stream()
                    .filter(
                            b -> b.personnePhysique() != null)
                    .toList();
            case "beneficiaires_effectifs" -> allBeneficiaires.stream()
                    .filter(
                            b -> b.personnePhysique() != null && b.pourcentageDetention() > 25)
                    .toList();
            default -> List.of();
        };
    }
    
}