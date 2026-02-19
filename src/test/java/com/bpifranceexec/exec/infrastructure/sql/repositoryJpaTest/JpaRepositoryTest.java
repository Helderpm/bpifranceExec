package com.bpifranceexec.exec.infrastructure.sql.repositoryJpaTest;

import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.repository.BeneficiaireJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.EntrepriseJpaRepository;
import com.bpifranceexec.exec.infrastructure.sql.repository.PersonnePhysiqueJpaRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext
class JpaRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Nested
    class EntrepriseJpaRepositoryTests {
        @Autowired
        private EntrepriseJpaRepository entrepriseJpaRepository;
        
        @Test
        void entrepriseRepository_shouldPersistAndFindByIdUsingEntityManager() {
            EntrepriseJpaEntity entreprise = new EntrepriseJpaEntity(null, "Test Entreprise", "12345678901234");
            
            EntrepriseJpaEntity savedEntity = testEntityManager.persistAndFlush(entreprise);
            
            assertThat(savedEntity.getEntrepriseId()).isNotNull();
            
            Optional<EntrepriseJpaEntity> foundEntity = entrepriseJpaRepository.findById(savedEntity.getEntrepriseId());
            assertThat(foundEntity).isPresent();
            assertThat(foundEntity.get().getNom()).isEqualTo("Test Entreprise");
            
            var foundEntityByNom = entrepriseJpaRepository.findByNom(savedEntity.getNom());
            assertThat(foundEntityByNom.getFirst().getNom()).isEqualTo("Test Entreprise");
        }
    }

    @Nested
    class PersonnePhysiqueJpaRepositoryTests {
        @Autowired
        private PersonnePhysiqueJpaRepository personnePhysiqueJpaRepository;
        
        @Test
        void personnePhysiqueRepository_shouldPersistAndFindByIdUsingEntityManager() {
            PersonnePhysiqueJpaEntity personnePhysiqueJpa = new PersonnePhysiqueJpaEntity(null, "Doe", "Jonh");
            
            PersonnePhysiqueJpaEntity savedEntity = testEntityManager.persistAndFlush(personnePhysiqueJpa);
            
            assertThat(savedEntity.getPersonneId()).isNotNull();
            
            Optional<PersonnePhysiqueJpaEntity> foundEntity = personnePhysiqueJpaRepository.findById(savedEntity.getPersonneId());
            assertThat(foundEntity).isPresent();
            assertThat(foundEntity.get().getNom()).isEqualTo("Doe");
            assertThat(foundEntity.get().getPrenom()).isEqualTo("Jonh");
            
            var foundEntityByNom = personnePhysiqueJpaRepository.findByNom(savedEntity.getNom());
            assertThat(foundEntityByNom.getFirst().getNom()).isEqualTo("Doe");
        }
    
    }

    @Nested
    class BeneficiaireJpaRepositoryJpaRepositoryTests {
        @Autowired
        private BeneficiaireJpaRepository beneficiaireJpaRepository;
        
        @Test
        void beneficiaireRepository_shouldPersistAndFindByEntrepriseMereId() {
            EntrepriseJpaEntity entrepriseMere = new EntrepriseJpaEntity(null, "Entreprise Mere", "111");
            EntrepriseJpaEntity entrepriseFille = new EntrepriseJpaEntity(null, "Entreprise Fille", "1234567890");
            PersonnePhysiqueJpaEntity personnePhysique = new PersonnePhysiqueJpaEntity(null, "Test", "User");
            
            // Use TestEntityManager to persist parent entities first
            testEntityManager.persist(entrepriseMere);
            testEntityManager.persist(entrepriseFille);
            testEntityManager.persist(personnePhysique);
            
            int pourcentageDetention = 50;
            BeneficiaireJpaEntity beneficiaire = new BeneficiaireJpaEntity(null, entrepriseMere, personnePhysique, entrepriseFille, pourcentageDetention);
            
            // Use TestEntityManager to persist the dependent entity
            BeneficiaireJpaEntity savedEntity = testEntityManager.persistAndFlush(beneficiaire);
            
            
            Optional<BeneficiaireJpaEntity> foundEntity = beneficiaireJpaRepository.findById(savedEntity.getBeneficiaireId());
            assertThat(foundEntity).isPresent();
            
            // Now, use the repository to query the data
            List<BeneficiaireJpaEntity> benefs = beneficiaireJpaRepository.findByEntrepriseMere_EntrepriseId(entrepriseMere.getEntrepriseId());
            assertThat(benefs).hasSize(1);
            assertThat(benefs.getFirst().getEntrepriseMere().getNom()).isEqualTo("Entreprise Mere");
            assertThat(benefs.getFirst().getEntrepriseFille().getNom()).isEqualTo("Entreprise Fille");
            assertThat(benefs.getFirst().getPourcentageDetention()).isEqualTo(pourcentageDetention);
        }
    }
}