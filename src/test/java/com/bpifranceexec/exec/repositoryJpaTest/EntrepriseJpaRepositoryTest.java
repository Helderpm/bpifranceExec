package com.bpifranceexec.exec.repositoryJpaTest;

import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.repository.EntrepriseJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the EntrepriseJpaRepository using @DataJpaTest.
 * This uses an in-memory H2 database and rolls back transactions after each test.
 */
@DataJpaTest
@DirtiesContext
@EntityScan("com.bpifranceexec.exec.infrastructure.dao")
@EnableJpaRepositories("com.bpifranceexec.exec.infrastructure.repository")
class EntrepriseJpaRepositoryTest {

@Autowired
private TestEntityManager entityManager;

@Autowired
private EntrepriseJpaRepository entrepriseRepository;

@Test
void whenSave_thenEntrepriseIsPersisted() {
    // Arrange
    EntrepriseJpaEntity ent = new EntrepriseJpaEntity();
    ent.setNom("BPI France");
    ent.setSiret("12345678900000");
    
    // Act
    EntrepriseJpaEntity saved = entrepriseRepository.save(ent);
    
    // Assert
    assertThat(saved).isNotNull();
    assertThat(saved.getId()).isNotNull();
    
    EntrepriseJpaEntity found = entityManager.find(EntrepriseJpaEntity.class, saved.getId());
    assertThat(found).isNotNull();
    assertThat(found.getNom()).isEqualTo("BPI France");
    assertThat(found.getSiret()).isEqualTo("12345678900000");
}

@Test
void whenFindById_withExistingEntity_thenReturnsCorrectEntity() {
    // Arrange
    EntrepriseJpaEntity ent = new EntrepriseJpaEntity();
    ent.setNom("ACME");
    ent.setSiret("11111111111111");
    EntrepriseJpaEntity persisted = entityManager.persistFlushFind(ent);
    
    // Act
    Optional<EntrepriseJpaEntity> foundOpt = entrepriseRepository.findById(persisted.getId());
    
    // Assert
    assertThat(foundOpt).isPresent();
    EntrepriseJpaEntity found = foundOpt.get();
    assertThat(found.getId()).isEqualTo(persisted.getId());
    assertThat(found.getNom()).isEqualTo("ACME");
    assertThat(found.getSiret()).isEqualTo("11111111111111");
}

@Test
void whenFindAll_withMultipleEntities_thenReturnsAllEntities() {
    // Arrange
    EntrepriseJpaEntity e1 = new EntrepriseJpaEntity();
    e1.setNom("ACME");
    e1.setSiret("11111111111111");
    entityManager.persist(e1);
    
    EntrepriseJpaEntity e2 = new EntrepriseJpaEntity();
    e2.setNom("Tech Solutions");
    e2.setSiret("22222222222222");
    entityManager.persist(e2);
    
    entityManager.flush();
    
    // Act
    List<EntrepriseJpaEntity> all = entrepriseRepository.findAll();
    
    // Assert
    assertThat(all).hasSize(2);
    assertThat(all).extracting(EntrepriseJpaEntity::getNom)
            .containsExactlyInAnyOrder("ACME", "Tech Solutions");
}

@Test
void whenUpdate_thenChangesArePersisted() {
    // Arrange
    EntrepriseJpaEntity ent = new EntrepriseJpaEntity();
    ent.setNom("Old Name");
    ent.setSiret("33333333333333");
    EntrepriseJpaEntity persisted = entityManager.persistFlushFind(ent);
    
    // Act
    persisted.setNom("New Name");
    EntrepriseJpaEntity updated = entrepriseRepository.save(persisted);
    entityManager.flush();
    
    // Assert
    EntrepriseJpaEntity found = entityManager.find(EntrepriseJpaEntity.class, updated.getId());
    assertThat(found.getNom()).isEqualTo("New Name");
    assertThat(found.getSiret()).isEqualTo("33333333333333");
}

@Test
void whenDelete_thenEntityIsRemoved() {
    // Arrange
    EntrepriseJpaEntity ent = new EntrepriseJpaEntity();
    ent.setNom("ToDelete");
    ent.setSiret("44444444444444");
    EntrepriseJpaEntity persisted = entityManager.persistFlushFind(ent);
    
    // Act
    entrepriseRepository.delete(persisted);
    entityManager.flush();
    
    // Assert
    Optional<EntrepriseJpaEntity> foundOpt = entrepriseRepository.findById(persisted.getId());
    assertThat(foundOpt).isNotPresent();
}
}