package com.bpifranceexec.exec.exposition.mapper;


import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import com.bpifranceexec.exec.exposition.rest.mapper.EntrepriseDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntrepriseDtoMapperTest {

private EntrepriseDtoMapper mapper;
    
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EntrepriseDtoMapper.class);
    }
    
    @Test
    void toDto_shouldMapDomainToDto() {
        // Arrange
        Entreprise domain = new Entreprise(1L, "TestCorp", "12345678901234");
        
        // Act
        EntrepriseDto dto = mapper.toDto(domain);
        
        // Assert
        assertNotNull(dto);
        assertEquals(domain.id(), dto.entrepriseDtoId());
        assertEquals(domain.nom(), dto.entrepriseDtoNom());
        assertEquals(domain.siret(), dto.entrepriseDtoSiret());
    }
    
    @Test
    void toDomain_shouldMapDtoToDomain() {
        // Arrange
        EntrepriseDto dto = new EntrepriseDto(1L, "TestCorp", "12345678901234");
        
        // Act
        Entreprise domain = mapper.toDomain(dto);
        
        // Assert
        assertNotNull(domain);
        assertEquals(dto.entrepriseDtoId(), domain.id());
        assertEquals(dto.entrepriseDtoNom(), domain.nom());
        assertEquals(dto.entrepriseDtoSiret(), domain.siret());
    }
}
