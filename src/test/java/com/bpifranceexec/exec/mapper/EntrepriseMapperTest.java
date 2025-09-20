package com.bpifranceexec.exec.mapper;

import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.mapper.EntrepriseMapper;
import com.bpifranceexec.exec.infrastructure.sql.mapper.EntrepriseMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class EntrepriseMapperTest {

private EntrepriseMapper entrepriseMapper;

    @BeforeEach
    void setUp() {
        // MapStruct generates this implementation at compile-time.
        entrepriseMapper = new EntrepriseMapperImpl();
    }
    
    @Test
    void toDomain_shouldMapAllFieldsCorrectly() {
        // Given
        EntrepriseJpaEntity entity = new EntrepriseJpaEntity();
        entity.setEntrepriseId(1L);
        entity.setNom("Tech Solutions");
        entity.setSiret("12345678901234");
        
        // When
        Entreprise domain = entrepriseMapper.toDomain(entity);
        
        // Then
        assertThat(domain.id()).isEqualTo(1L);
        assertThat(domain.nom()).isEqualTo("Tech Solutions");
        assertThat(domain.siret()).isEqualTo("12345678901234");
    }
    
    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        // Given
        Entreprise domain = new Entreprise(
                2L,
                "Innovate Corp",
                "43210987654321"
        );
        
        // When
        EntrepriseJpaEntity entity = entrepriseMapper.toEntity(domain);
        
        // Then
        assertThat(entity.getEntrepriseId()).isEqualTo(2L);
        assertThat(entity.getNom()).isEqualTo("Innovate Corp");
        assertThat(entity.getSiret()).isEqualTo("43210987654321");
    }
}