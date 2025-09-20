package com.bpifranceexec.exec.repositoryJpaTest;

import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.repository.PersonnePhysiqueJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the PersonnePhysiqueJpaRepository.
 * The @DataJpaTest annotation provides a pre-configured test environment for JPA repositories.
 * It uses an in-memory H2 database and rolls back each transaction after the test,
 * ensuring tests are isolated and independent.
 */
@DataJpaTest
@EntityScan("com.bpifranceexec.exec.infrastructure.dao")
@EnableJpaRepositories("com.bpifranceexec.exec.infrastructure.repository")
class PersonnePhysiqueJpaRepositoryTest {

/**
 * TestEntityManager is a utility provided by Spring Boot specifically for JPA tests.
 * It allows us to manage our entities within the test's persistence context
 * without interfering with the repository itself.
 */
@Autowired
private TestEntityManager entityManager;

@Autowired
private PersonnePhysiqueJpaRepository personnePhysiqueRepository;

@Test
void whenFindById_withExistingEntity_thenReturnsCorrectEntity() {
    // Arrange: Create and persist a test entity
    PersonnePhysiqueJpaEntity personneToSave = new PersonnePhysiqueJpaEntity();
    personneToSave.setNom("Dupont");
    personneToSave.setPrenom("Jean");
    PersonnePhysiqueJpaEntity savedPersonne = entityManager.persistFlushFind(personneToSave);
    
    // Act: Use the repository to find the entity
    Optional<PersonnePhysiqueJpaEntity> foundPersonneOpt = personnePhysiqueRepository.findById(savedPersonne.getId());
    
    // Assert: Verify the result
    assertThat(foundPersonneOpt).isPresent();
    PersonnePhysiqueJpaEntity foundPersonne = foundPersonneOpt.get();
    assertThat(foundPersonne.getId()).isEqualTo(savedPersonne.getId());
    assertThat(foundPersonne.getNom()).isEqualTo("Dupont");
    assertThat(foundPersonne.getPrenom()).isEqualTo("Jean");
}

@Test
void whenFindAll_withMultipleEntities_thenReturnsAllEntities() {
    // Arrange: Create and persist multiple entities
    PersonnePhysiqueJpaEntity personne1 = new PersonnePhysiqueJpaEntity();
    personne1.setNom("Martin");
    personne1.setPrenom("Alice");
    entityManager.persist(personne1);
    
    PersonnePhysiqueJpaEntity personne2 = new PersonnePhysiqueJpaEntity();
    personne2.setNom("Bernard");
    personne2.setPrenom("Luc");
    entityManager.persist(personne2);
    
    entityManager.flush();
    
    // Act: Use the repository to find all entities
    List<PersonnePhysiqueJpaEntity> personnes = personnePhysiqueRepository.findAll();
    
    // Assert: Verify the list contains both entities
    assertThat(personnes).hasSize(2);
    assertThat(personnes).extracting(PersonnePhysiqueJpaEntity::getNom).containsExactlyInAnyOrder("Martin", "Bernard");
}

@Test
void whenSave_thenEntityIsPersisted() {
    // Arrange: Create a new entity instance
    PersonnePhysiqueJpaEntity newPersonne = new PersonnePhysiqueJpaEntity();
    newPersonne.setNom("Durand");
    newPersonne.setPrenom("Sophie");
    
    // Act: Use the repository to save the new entity
    PersonnePhysiqueJpaEntity savedPersonne = personnePhysiqueRepository.save(newPersonne);
    
    // Assert: Verify the entity was saved and has an ID
    assertThat(savedPersonne).isNotNull();
    assertThat(savedPersonne.getId()).isNotNull();
    
    // Optional: Verify it can be retrieved from the database
    PersonnePhysiqueJpaEntity foundInDb = entityManager.find(PersonnePhysiqueJpaEntity.class, savedPersonne.getId());
    assertThat(foundInDb).isNotNull();
    assertThat(foundInDb.getNom()).isEqualTo("Durand");
}

@Test
void whenDelete_thenEntityIsRemoved() {
    // Arrange: Create and persist an entity to delete
    PersonnePhysiqueJpaEntity personneToDelete = new PersonnePhysiqueJpaEntity();
    personneToDelete.setNom("Moreau");
    personneToDelete.setPrenom("Pierre");
    PersonnePhysiqueJpaEntity savedPersonne = entityManager.persistFlushFind(personneToDelete);
    UUID id = savedPersonne.getId();
    
    // Act: Delete the entity using the repository
    personnePhysiqueRepository.delete(savedPersonne);
    entityManager.flush(); // Ensure the delete operation is sent to the DB
    
    // Assert: Verify the entity can no longer be found
    Optional<PersonnePhysiqueJpaEntity> deletedPersonneOpt = personnePhysiqueRepository.findById(id);
    assertThat(deletedPersonneOpt).isNotPresent();
}
}
