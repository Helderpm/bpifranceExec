package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PersonnePhysiqueMapperTest {

private PersonnePhysiqueMapper personnePhysiqueMapper;

    @BeforeEach
    void setUp() {
        // MapStruct generates this implementation at compile-time.
        personnePhysiqueMapper = new PersonnePhysiqueMapperImpl();
    }
    
    @Test
    void toDomain_shouldMapAllFieldsCorrectly() {
        // Given
        PersonnePhysiqueJpaEntity entity = new PersonnePhysiqueJpaEntity();
        entity.setPersonneId(10L);
        entity.setNom("Doe");
        entity.setPrenom("John");
        
        // When
        PersonnePhysique domain = personnePhysiqueMapper.toDomain(entity);
        
        // Then
        assertThat(domain.id()).isEqualTo(10L);
        assertThat(domain.nom()).isEqualTo("Doe");
        assertThat(domain.prenom()).isEqualTo("John");
    }
    
    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        // Given
        PersonnePhysique domain = new PersonnePhysique(
                20L,
                "Smith",
                "Jane"
        );
        
        // When
        PersonnePhysiqueJpaEntity entity = personnePhysiqueMapper.toEntity(domain);
        
        // Then
        assertThat(entity.getPersonneId()).isEqualTo(20L);
        assertThat(entity.getNom()).isEqualTo("Smith");
        assertThat(entity.getPrenom()).isEqualTo("Jane");
    }
}