package com.bpifranceexec.exec.infrastructure.sql.adapter;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.mapper.BeneficiaireMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.EntrepriseMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.PersonnePhysiqueMapper;
import com.bpifranceexec.exec.infrastructure.sql.repository.BeneficiaireJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.EntrepriseJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.PersonnePhysiqueJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BeneficiaireJPAServiceImp implements BeneficiaireRepositoryPort {

    private final BeneficiaireJpaRepository beneficiaireJpaRepository;
    private final BeneficiaireMapper beneficiaireMapper;
    private final EntrepriseJpaRepository entrepriseJpaRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final PersonnePhysiqueJpaRepository personnePhysiqueJpaRepository;
    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    @Override
    public List<Beneficiaire> findByEntrepriseMereId(Long entrepriseMereId) {
        // Updated method call
        return beneficiaireJpaRepository.findByEntrepriseMere_EntrepriseId(entrepriseMereId)
                .stream()
                .map(beneficiaireMapper::toDomain)
                .toList();
    }
    
    @Override
    public Beneficiaire saveBeneficiaire(Beneficiaire beneficiaire) {
        var beneficiaireToSave = beneficiaireMapper.toEntity(beneficiaire);
        var savedEntity = beneficiaireJpaRepository.save(beneficiaireToSave);
        return beneficiaireMapper.toDomain(savedEntity);
    }
    
    @Override
    public Entreprise saveEntreprise(Entreprise entreprise) {
        var entrepriseToSave  = entrepriseMapper.toEntity(entreprise);
        EntrepriseJpaEntity savedEntity = entrepriseJpaRepository.save(entrepriseToSave);
        return entrepriseMapper.toDomain(savedEntity);
    }
    
    @Override
    public PersonnePhysique savePersonnePhysique(PersonnePhysique personnePhysique) {
        var personnePhysiqueToSave  = personnePhysiqueMapper.toEntity(personnePhysique);
        PersonnePhysiqueJpaEntity savedEntity = personnePhysiqueJpaRepository.save(personnePhysiqueToSave);
        return personnePhysiqueMapper.toDomain(savedEntity);
    }
}