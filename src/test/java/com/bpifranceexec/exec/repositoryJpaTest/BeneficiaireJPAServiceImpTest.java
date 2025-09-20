package com.bpifranceexec.exec.repositoryJpaTest;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.infrastructure.sql.adapter.BeneficiaireJPAServiceImp;
import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.mapper.BeneficiaireMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.EntrepriseMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.PersonnePhysiqueMapper;
import com.bpifranceexec.exec.infrastructure.sql.repository.BeneficiaireJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.EntrepriseJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.PersonnePhysiqueJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficiaireJPAServiceImpTest {
    
    @Mock
    private BeneficiaireJpaRepository beneficiaireJpaRepository;
    
    @Mock
    private BeneficiaireMapper beneficiaireMapper;
    
    @Mock
    private EntrepriseJpaRepository entrepriseJpaRepository;
    
    @Mock
    private EntrepriseMapper entrepriseMapper;
    
    @Mock
    private PersonnePhysiqueJpaRepository personnePhysiqueJpaRepository;
    
    @Mock
    private PersonnePhysiqueMapper personnePhysiqueMapper;
    
    @InjectMocks
    private BeneficiaireJPAServiceImp beneficiaireJPAServiceImp;
    
    private BeneficiaireJpaEntity beneficiaireJpaEntity;
    private Beneficiaire beneficiaireDomain;
    private EntrepriseJpaEntity entrepriseJpaEntity;
    private Entreprise entrepriseDomain;
    private PersonnePhysiqueJpaEntity personnePhysiqueJpaEntity;
    private PersonnePhysique personnePhysiqueDomain;
    
    @BeforeEach
    void setUp() {
        beneficiaireJpaEntity = new BeneficiaireJpaEntity();
        beneficiaireDomain = new Beneficiaire(1L, null, null, null, 50);
        
        entrepriseJpaEntity = new EntrepriseJpaEntity();
        entrepriseDomain = new Entreprise(2L, "Entreprise Test", "12345");
        
        personnePhysiqueJpaEntity = new PersonnePhysiqueJpaEntity();
        personnePhysiqueDomain = new PersonnePhysique(3L, "Doe", "John");
    }
    
    @Test
    void findByEntrepriseMereId_shouldReturnBeneficiairesList() {
        // Given
        Long entrepriseId = 1L;
        List<BeneficiaireJpaEntity> entities = List.of(beneficiaireJpaEntity);
        when(beneficiaireJpaRepository.findByEntrepriseMere_EntrepriseId(entrepriseId)).thenReturn(entities);
        when(beneficiaireMapper.toDomain(beneficiaireJpaEntity)).thenReturn(beneficiaireDomain);
        
        // When
        List<Beneficiaire> result = beneficiaireJPAServiceImp.findByEntrepriseMereId(entrepriseId);
        
        // Then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(beneficiaireDomain);
        verify(beneficiaireJpaRepository).findByEntrepriseMere_EntrepriseId(entrepriseId);
        verify(beneficiaireMapper).toDomain(beneficiaireJpaEntity);
    }
    
    @Test
    void save_shouldSaveBeneficiaireCorrectly() {
        // Given
        when(beneficiaireMapper.toEntity(beneficiaireDomain)).thenReturn(beneficiaireJpaEntity);
        when(beneficiaireJpaRepository.save(beneficiaireJpaEntity)).thenReturn(beneficiaireJpaEntity);
        when(beneficiaireMapper.toDomain(beneficiaireJpaEntity)).thenReturn(beneficiaireDomain);
        
        // When
        Beneficiaire savedBeneficiaire = beneficiaireJPAServiceImp.saveBeneficiaire(beneficiaireDomain);
        
        // Then
        assertThat(savedBeneficiaire).isEqualTo(beneficiaireDomain);
        verify(beneficiaireMapper).toEntity(beneficiaireDomain);
        verify(beneficiaireJpaRepository).save(beneficiaireJpaEntity);
        verify(beneficiaireMapper).toDomain(beneficiaireJpaEntity);
    }
    
    @Test
    void saveEntreprise_shouldSaveEntrepriseCorrectly() {
        // Given
        when(entrepriseMapper.toEntity(entrepriseDomain)).thenReturn(entrepriseJpaEntity);
        when(entrepriseJpaRepository.save(entrepriseJpaEntity)).thenReturn(entrepriseJpaEntity);
        when(entrepriseMapper.toDomain(entrepriseJpaEntity)).thenReturn(entrepriseDomain);
        
        // When
        Entreprise savedEntreprise = beneficiaireJPAServiceImp.saveEntreprise(entrepriseDomain);
        
        // Then
        assertThat(savedEntreprise).isEqualTo(entrepriseDomain);
        verify(entrepriseMapper).toEntity(entrepriseDomain);
        verify(entrepriseJpaRepository).save(entrepriseJpaEntity);
        verify(entrepriseMapper).toDomain(entrepriseJpaEntity);
    }
    
    @Test
    void savePersonnePhysique_shouldSavePersonnePhysiqueCorrectly() {
        // Given
        when(personnePhysiqueMapper.toEntity(personnePhysiqueDomain)).thenReturn(personnePhysiqueJpaEntity);
        when(personnePhysiqueJpaRepository.save(personnePhysiqueJpaEntity)).thenReturn(personnePhysiqueJpaEntity);
        when(personnePhysiqueMapper.toDomain(personnePhysiqueJpaEntity)).thenReturn(personnePhysiqueDomain);
        
        // When
        PersonnePhysique savedPersonnePhysique = beneficiaireJPAServiceImp.savePersonnePhysique(personnePhysiqueDomain);
        
        // Then
        assertThat(savedPersonnePhysique).isEqualTo(personnePhysiqueDomain);
        verify(personnePhysiqueMapper).toEntity(personnePhysiqueDomain);
        verify(personnePhysiqueJpaRepository).save(personnePhysiqueJpaEntity);
        verify(personnePhysiqueMapper).toDomain(personnePhysiqueJpaEntity);
    }
}
