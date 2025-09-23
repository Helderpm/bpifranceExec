package com.bpifranceexec.exec.exposition.mapper;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import com.bpifranceexec.exec.exposition.rest.mapper.BeneficiaireDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.EntrepriseDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.PersonnePhysiqueDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeneficiaireDtoMapperTest {
    
    @InjectMocks
    private BeneficiaireDtoMapper mapper = Mappers.getMapper(BeneficiaireDtoMapper.class);
    
    @Mock
    private EntrepriseDtoMapper entrepriseDtoMapper;
    
    @Mock
    private PersonnePhysiqueDtoMapper personnePhysiqueDtoMapper;
    
    @Test
    void toDto_shouldMapDomainToDtoWithNestedObjects() {
        // Arrange
        Entreprise entrepriseMere = new Entreprise(1L, "ParentCo", "123");
        PersonnePhysique personnePhysique = new PersonnePhysique(2L, "John", "Doe");
        EntrepriseDto entrepriseMereDto = new EntrepriseDto(1L, "ParentCo", "123");
        PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto(2L, "John", "Doe");
        Beneficiaire domain = new Beneficiaire(3L, entrepriseMere, personnePhysique, null, 50);
        
        when(entrepriseDtoMapper.toDto(any(Entreprise.class))).thenReturn(entrepriseMereDto);
        when(personnePhysiqueDtoMapper.toDto(any(PersonnePhysique.class))).thenReturn(personnePhysiqueDto);
        
        // Act
        BeneficiaireDto dto = mapper.toDto(domain);
        
        // Assert
        assertNotNull(dto);
        assertEquals(domain.id(), dto.beneficiaireDtoId());
        assertEquals(entrepriseMereDto, dto.entrepriseMereDto());
        assertEquals(personnePhysiqueDto, dto.personnePhysiqueDto());
        assertNull(dto.entrepriseFilleDto());
        assertEquals(domain.pourcentageDetention(), dto.pourcentageDetentionDto());
    }
    
    @Test
    void toDomain_shouldMapDtoToDomainWithNestedObjects() {
        // Arrange
        EntrepriseDto entrepriseMereDto = new EntrepriseDto(1L, "ParentCo", "123");
        PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto(2L, "John", "Doe");
        Entreprise entrepriseMere = new Entreprise(1L, "ParentCo", "123");
        PersonnePhysique personnePhysique = new PersonnePhysique(2L, "John", "Doe");
        BeneficiaireDto dto = new BeneficiaireDto(3L, entrepriseMereDto, personnePhysiqueDto, null, 50);
        
        when(entrepriseDtoMapper.toDomain(any(EntrepriseDto.class))).thenReturn(entrepriseMere);
        when(personnePhysiqueDtoMapper.toDomain(any(PersonnePhysiqueDto.class))).thenReturn(personnePhysique);
        
        // Act
        Beneficiaire domain = mapper.toDomain(dto);
        
        // Assert
        assertNotNull(domain);
        assertEquals(dto.beneficiaireDtoId(), domain.id());
        assertEquals(entrepriseMere, domain.entrepriseMere());
        assertEquals(personnePhysique, domain.personnePhysique());
        assertNull(domain.entrepriseFille());
        assertEquals(dto.pourcentageDetentionDto(), domain.pourcentageDetention());
    }
}