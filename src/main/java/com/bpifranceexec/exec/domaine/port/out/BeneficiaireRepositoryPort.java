package com.bpifranceexec.exec.domaine.port.out;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BeneficiaireRepositoryPort {
    
    List<Beneficiaire> findByEntrepriseMereId(Long entrepriseMereId);
    
    Beneficiaire save(Beneficiaire beneficiaire);

    Entreprise saveEntreprise(Entreprise entreprise);

    PersonnePhysique savePersonnePhysique(PersonnePhysique personnePhysique);
    
}