package com.bpifranceexec.exec.infrastructure.sql.adapter;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;
import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.mapper.BeneficiaireMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.EntrepriseMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.PersonnePhysiqueMapper;
import com.bpifranceexec.exec.infrastructure.sql.repository.BeneficiaireJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.EntrepriseJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.PersonnePhysiqueJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class BeneficiaireJPAServiceImp implements BeneficiaireRepositoryPort {

    private final BeneficiaireJpaRepository beneficiaireJpaRepository;
    private final BeneficiaireMapper beneficiaireMapper;
    private final EntrepriseJpaRepository entrepriseJpaRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final PersonnePhysiqueJpaRepository personnePhysiqueJpaRepository;
    private final PersonnePhysiqueMapper personnePhysiqueMapper;
    
    @Override
    public List<Beneficiaire> findByEntrepriseMereId(Long entrepriseMereId) {
        log.info("Fetching beneficiaries for entrepriseMereId: {}", entrepriseMereId);
        List<Beneficiaire> beneficiaires = beneficiaireJpaRepository.findByEntrepriseMere_EntrepriseId(entrepriseMereId)
                .stream()
                .map(beneficiaireMapper::toDomain)
                .toList();
        log.info("Found {} beneficiaries for entrepriseMereId: {}", beneficiaires.size(), entrepriseMereId);
        return beneficiaires;
    }
    
    @Override
    public Beneficiaire saveBeneficiaire(Beneficiaire beneficiaire) {
        log.info("Attempting to save new beneficiaire with pourcentage: {}", beneficiaire.pourcentageDetention());
        
        // The method uses null checks before calling findById
        Long entrepriseMereId = beneficiaire.entrepriseMere() != null ? beneficiaire.entrepriseMere().id() : null;
        Long personnePhysiqueId = beneficiaire.personnePhysique() != null ? beneficiaire.personnePhysique().id() : null;
        Long entrepriseFilleId = beneficiaire.entrepriseFille() != null ? beneficiaire.entrepriseFille().id() : null;
        
        log.debug("Looking up entities for IDs: entrepriseMere={}, personnePhysique={}, entrepriseFille={}",
                entrepriseMereId, personnePhysiqueId, entrepriseFilleId);
        
        var managedEntrepriseMere = beneficiaire.entrepriseMere() != null ?
                entrepriseJpaRepository.findById(entrepriseMereId).orElse(null) : null;
        
        var managedPersonnePhysique = beneficiaire.personnePhysique() != null ?
                personnePhysiqueJpaRepository.findById(personnePhysiqueId).orElse(null) : null;
        
        var managedEntrepriseFille = beneficiaire.entrepriseFille() != null ?
                entrepriseJpaRepository.findById(entrepriseFilleId).orElse(null) : null;
        
        var beneficiaireToSave = new BeneficiaireJpaEntity(
                null,
                managedEntrepriseMere,
                managedPersonnePhysique,
                managedEntrepriseFille,
                beneficiaire.pourcentageDetention()
        );
        
        var savedEntity = beneficiaireJpaRepository.save(beneficiaireToSave);
        log.info("Bénéficiaire saved successfully with ID: {}", savedEntity.getBeneficiaireId());
        return beneficiaireMapper.toDomain(savedEntity);
    }
    
    @Override
    public Entreprise saveEntreprise(Entreprise entreprise) {
        log.info("Attempting to save new entreprise: {}", entreprise.nom());
        var entrepriseToSave = entrepriseMapper.toEntity(entreprise);
        EntrepriseJpaEntity savedEntity = entrepriseJpaRepository.save(entrepriseToSave);
        log.info("Entreprise saved successfully with ID: {}", savedEntity.getEntrepriseId());
        return entrepriseMapper.toDomain(savedEntity);
    }
    
    @Override
    public PersonnePhysique savePersonnePhysique(PersonnePhysique personnePhysique) {
        log.info("Attempting to save new personne physique: {} {}", personnePhysique.prenom(), personnePhysique.nom());
        var personnePhysiqueToSave = personnePhysiqueMapper.toEntity(personnePhysique);
        PersonnePhysiqueJpaEntity savedEntity = personnePhysiqueJpaRepository.save(personnePhysiqueToSave);
        log.info("Personne physique saved successfully with ID: {}", savedEntity.getPersonneId());
        return personnePhysiqueMapper.toDomain(savedEntity);
    }
    
    @Override
    public boolean existsPersonnePhysiqueById(Long personneId) {
        if (personneId == null) {
            throw new IllegalArgumentException("Personne ID cannot be null");
        }
        return personnePhysiqueJpaRepository.existsById(personneId);
    }
    
    @Override
    public boolean existsEntrepriseById(Long entrepriseId) {
        if (entrepriseId == null) {
            throw new IllegalArgumentException("Entreprise ID cannot be null");
        }
        return entrepriseJpaRepository.existsById(entrepriseId);
    }
    
}
