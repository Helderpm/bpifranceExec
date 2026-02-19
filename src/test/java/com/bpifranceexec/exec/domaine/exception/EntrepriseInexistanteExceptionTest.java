package com.bpifranceexec.exec.domaine.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour {@link EntrepriseInexistanteException}.
 * <p>
 * Ces tests vérifient le comportement de l'exception entreprise inexistante
 * dans différents scénarios d'utilisation.
 * </p>
 */
@DisplayName("Tests de EntrepriseInexistanteException")
class EntrepriseInexistanteExceptionTest {

    @Test
    @DisplayName("Doit créer l'exception avec l'ID d'entreprise")
    void shouldCreateExceptionWithEntrepriseId() {
        // Given
        Long entrepriseId = 123L;

        // When
        EntrepriseInexistanteException exception = new EntrepriseInexistanteException(entrepriseId);

        // Then
        assertThat(exception.getMessage()).contains("Entreprise inexistante: 123");
        assertThat(exception.getEntrepriseId()).isEqualTo(entrepriseId);
    }

    @Test
    @DisplayName("Doit gérer un ID d'entreprise null")
    void shouldHandleNullEntrepriseId() {
        // Given
        Long entrepriseIdNull = null;

        // When
        EntrepriseInexistanteException exception = new EntrepriseInexistanteException(entrepriseIdNull);

        // Then
        assertThat(exception.getMessage()).contains("Entreprise inexistante: null");
        assertThat(exception.getEntrepriseId()).isNull();
    }

    @Test
    @DisplayName("Doit gérer un ID d'entreprise zéro")
    void shouldHandleZeroEntrepriseId() {
        // Given
        Long entrepriseIdZero = 0L;

        // When
        EntrepriseInexistanteException exception = new EntrepriseInexistanteException(entrepriseIdZero);

        // Then
        assertThat(exception.getMessage()).contains("Entreprise inexistante: 0");
        assertThat(exception.getEntrepriseId()).isEqualTo(entrepriseIdZero);
    }

    @Test
    @DisplayName("Doit gérer un ID d'entreprise négatif")
    void shouldHandleNegativeEntrepriseId() {
        // Given
        Long entrepriseIdNegatif = -1L;

        // When
        EntrepriseInexistanteException exception = new EntrepriseInexistanteException(entrepriseIdNegatif);

        // Then
        assertThat(exception.getMessage()).contains("Entreprise inexistante: -1");
        assertThat(exception.getEntrepriseId()).isEqualTo(entrepriseIdNegatif);
    }

    @Test
    @DisplayName("Doit être une RuntimeException")
    void shouldBeRuntimeException() {
        // Given
        EntrepriseInexistanteException exception = new EntrepriseInexistanteException(999L);

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Doit conserver le message d'erreur original")
    void shouldPreserveOriginalErrorMessage() {
        // Given
        Long entrepriseId = 456L;

        // When
        EntrepriseInexistanteException exception = new EntrepriseInexistanteException(entrepriseId);

        // Then
        assertThat(exception.getMessage()).isEqualTo("Entreprise inexistante: 456");
    }
}
