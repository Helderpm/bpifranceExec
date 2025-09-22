package com.bpifranceexec.exec.exposition.mapper;


import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import com.bpifranceexec.exec.exposition.rest.mapper.PersonnePhysiqueDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonnePhysiqueDtoMapperTest {

    private PersonnePhysiqueDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PersonnePhysiqueDtoMapper.class);
    }
    
    @Test
    void toDto_shouldMapDomainToDto() {
        // Arrange
        PersonnePhysique domain = new PersonnePhysique(1L, "Doe", "John");
        
        // Act
        PersonnePhysiqueDto dto = mapper.toDto(domain);
        
        // Assert
        assertNotNull(dto);
        assertEquals(domain.id(), dto.personneDtoId());
        assertEquals(domain.nom(), dto.personneDtoNom());
        assertEquals(domain.prenom(), dto.personneDtoPrenom());
    }
    
    @Test
    void toDomain_shouldMapDtoToDomain() {
        // Arrange
        PersonnePhysiqueDto dto = new PersonnePhysiqueDto(1L, "Doe", "John");
        
        // Act
        PersonnePhysique domain = mapper.toDomain(dto);
        
        // Assert
        assertNotNull(domain);
        assertEquals(dto.personneDtoId(), domain.id());
        assertEquals(dto.personneDtoNom(), domain.nom());
        assertEquals(dto.personneDtoPrenom(), domain.prenom());
    }
}