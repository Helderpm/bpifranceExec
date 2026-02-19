package com.bpifranceexec.exec.infrastructure.sql.mapper;
import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BeneficiaireMapperTest {

private BeneficiaireMapper mapper;

    @BeforeEach
    void setUp() {
        // Instantiate the MapStruct generated implementation
        mapper = new BeneficiaireMapperImpl();
    }

@Test
void toDomain_shouldMapAllFieldsCorrectly() {
    // Given
    BeneficiaireJpaEntity entity = new BeneficiaireJpaEntity();
    entity.setBeneficiaireId(1L);
    entity.setPourcentageDetention(50);
    // Assuming EntrepriseJpaEntity and PersonnePhysiqueJpaEntity exist
    // For this test, we can use mocks or simple instances
    EntrepriseJpaEntity entrepriseMere = new EntrepriseJpaEntity();
    EntrepriseJpaEntity entrepriseFille = new EntrepriseJpaEntity();
    PersonnePhysiqueJpaEntity personnePhysique = new PersonnePhysiqueJpaEntity();
    
    entity.setEntrepriseMere(entrepriseMere);
    entity.setEntrepriseFille(entrepriseFille);
    entity.setPersonnePhysique(personnePhysique);
    
    // When
    Beneficiaire domain = mapper.toDomain(entity);
    
    // Then
    assertThat(domain.id()).isEqualTo(1L);
    assertThat(domain.pourcentageDetention()).isEqualTo(50);
    assertThat(domain.entrepriseMere()).isNotNull();
    assertThat(domain.entrepriseFille()).isNotNull();
    assertThat(domain.personnePhysique()).isNotNull();
}

@Test
void toEntity_shouldMapAllFieldsCorrectly() {
    // Given
    Beneficiaire domain = new Beneficiaire(
            2L,
            new Entreprise(null, null, null), // simplified for test
            new PersonnePhysique(null, null, null), // simplified for test
            new Entreprise(null, null, null), // simplified for test
            75
    );
    
    // When
    BeneficiaireJpaEntity entity = mapper.toEntity(domain);
    
    // Then
    assertThat(entity.getBeneficiaireId()).isEqualTo(2L);
    assertThat(entity.getPourcentageDetention()).isEqualTo(75);
    assertThat(entity.getEntrepriseMere()).isNotNull();
    assertThat(entity.getEntrepriseFille()).isNotNull();
    assertThat(entity.getPersonnePhysique()).isNotNull();
}
}