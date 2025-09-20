package com.bpifranceexec.exec.domaine.port.in;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface GestionBeneficiairePort {
    
    Optional<Entreprise> creerEntreprise(Entreprise entreprise);
    
    Optional<PersonnePhysique> creerPersonnePhysique(PersonnePhysique personne);
    
    Optional<Beneficiaire> ajouterBeneficiaire(Beneficiaire beneficiaire);
    
    List<Beneficiaire> recupererBeneficiaires(Long entrepriseId, String type);
}
