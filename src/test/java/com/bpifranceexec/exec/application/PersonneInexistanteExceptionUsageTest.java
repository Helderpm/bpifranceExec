package com.bpifranceexec.exec.application;

import com.bpifranceexec.exec.domaine.exception.PersonneInexistanteException;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests démontrant l'utilisation de {@link PersonneInexistanteException}.
 * <p>
 * Ces tests montrent quand et comment l'exception serait utilisée
 * dans des scénarios réels de validation.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests d'utilisation de PersonneInexistanteException")
class PersonneInexistanteExceptionUsageTest {

    @Mock
    private BeneficiaireRepositoryPort beneficiaireRepositoryPort;

    private GestionBeneficiaireService service;

    @BeforeEach
    void setUp() {
        service = new GestionBeneficiaireService(beneficiaireRepositoryPort);
    }

    @Test
    @DisplayName("Doit créer PersonneInexistanteException avec ID valide")
    void shouldCreatePersonneInexistanteExceptionWithValidId() {
        // Given
        Long personneIdInexistante = 999L;

        // When
        PersonneInexistanteException exception = new PersonneInexistanteException(personneIdInexistante);

        // Then
        assertThatThrownBy(() -> { throw exception; })
                .isInstanceOf(PersonneInexistanteException.class)
                .hasMessageContaining("Personne physique inexistante: 999");
        assertThat(exception.getPersonneId()).isEqualTo(personneIdInexistante);
    }

    @Test
    @DisplayName("Doit créer PersonneInexistanteException avec ID null")
    void shouldCreatePersonneInexistanteExceptionWithNullId() {
        // Given
        Long personneIdNull = null;

        // When
        PersonneInexistanteException exception = new PersonneInexistanteException(personneIdNull);

        // Then
        assertThatThrownBy(() -> { throw exception; })
                .isInstanceOf(PersonneInexistanteException.class)
                .hasMessageContaining("Personne physique inexistante: null");
        assertThat(exception.getPersonneId()).isNull();
    }

    @Test
    @DisplayName("Doit être une RuntimeException")
    void shouldBeRuntimeException() {
        // Given
        PersonneInexistanteException exception = new PersonneInexistanteException(123L);

        // Then
        assertThatThrownBy(() -> { throw exception; })
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Doit conserver le message d'erreur original")
    void shouldPreserveOriginalErrorMessage() {
        // Given
        Long personneId = 456L;

        // When
        PersonneInexistanteException exception = new PersonneInexistanteException(personneId);

        // Then
        assertThat(exception.getMessage()).isEqualTo("Personne physique inexistante: 456");
    }

    /**
     * Test démontrant quand cette exception pourrait être utilisée dans un service.
     * Ce test montre un scénario hypothétique où on vérifie l'existence d'une personne.
     */
    @Test
    @DisplayName("Scénario hypothétique d'utilisation dans le service")
    void hypotheticalUsageScenario() {
        // Given
        PersonnePhysique personneInexistante = new PersonnePhysique(999L, "Inexistante", "Personne");

        // Then - L'exception serait levée si la personne n'existait pas
        PersonneInexistanteException exception = new PersonneInexistanteException(personneInexistante.id());
        
        assertThatThrownBy(() -> { throw exception; })
                .isInstanceOf(PersonneInexistanteException.class)
                .hasMessageContaining("Personne physique inexistante: 999");
    }
}
