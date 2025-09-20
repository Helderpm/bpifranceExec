package com.bpifranceexec.exec.mapper;

import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.mapper.EntrepriseMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the EntrepriseMapper.
 * This test verifies that the mapping between the EntrepriseJpaEntity and the Entreprise domain model is correct.
 */
class EntrepriseMapperTest {

    private final EntrepriseMapper mapper = EntrepriseMapper.INSTANCE;
    
    @Test
    void shouldMapEntityToDomainCorrectly() {
        // Arrange: Create a source JPA entity
        EntrepriseJpaEntity entity = new EntrepriseJpaEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        entity.setNom("Test Entreprise");
        entity.setSiret("12345678901234");
        
        // Act: Map the entity to a domain object
        Entreprise domain = mapper.toDomain(entity);
        
        // Assert: Verify that all fields were mapped correctly
        assertThat(domain).isNotNull();
        assertThat(domain.id()).isEqualTo(id);
        assertThat(domain.nom()).isEqualTo("Test Entreprise");
        assertThat(domain.siret()).isEqualTo("12345678901234");
    }
    
    @Test
    void shouldMapDomainToEntityCorrectly() {
        // Arrange: Create a source domain object
        UUID id = UUID.randomUUID();
        Entreprise domain = new Entreprise(id, "Domain Corp", "98765432109876");
        
        // Act: Map the domain object to an entity
        EntrepriseJpaEntity entity = mapper.toEntity(domain);
        
        // Assert: Verify that all fields were mapped correctly
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getNom()).isEqualTo("Domain Corp");
        assertThat(entity.getSiret()).isEqualTo("98765432109876");
    }
    
    @Test
    void shouldReturnNullWhenMappingNullEntityToDomain() {
        // Act: Map a null entity
        Entreprise domain = mapper.toDomain(null);
        
        // Assert: The result should be null
        assertThat(domain).isNull();
    }
    
    @Test
    void shouldReturnNullWhenMappingNullDomainToEntity() {
        // Act: Map a null domain object
        EntrepriseJpaEntity entity = mapper.toEntity(null);
        
        // Assert: The result should be null
        assertThat(entity).isNull();
    }
}
