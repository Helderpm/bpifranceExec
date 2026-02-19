package com.bpifranceexec.exec.domaine.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour {@link SiretInvalideException}.
 * <p>
 * Ces tests vérifient le comportement de l'exception SIRET invalide
 * dans différents scénarios d'utilisation.
 * </p>
 */
@DisplayName("Tests de SiretInvalideException")
class SiretInvalideExceptionTest {

    @Test
    @DisplayName("Doit créer l'exception avec le SIRET invalide")
    void shouldCreateExceptionWithInvalidSiret() {
        // Given
        String siretInvalide = "123";

        // When
        SiretInvalideException exception = new SiretInvalideException(siretInvalide);

        // Then
        assertThat(exception.getMessage()).contains("SIRET invalide: 123");
        assertThat(exception.getSiret()).isEqualTo(siretInvalide);
    }

    @Test
    @DisplayName("Doit gérer un SIRET null")
    void shouldHandleNullSiret() {
        // Given
        String siretNull = null;

        // When
        SiretInvalideException exception = new SiretInvalideException(siretNull);

        // Then
        assertThat(exception.getMessage()).contains("SIRET invalide: null");
        assertThat(exception.getSiret()).isNull();
    }

    @Test
    @DisplayName("Doit gérer un SIRET vide")
    void shouldHandleEmptySiret() {
        // Given
        String siretVide = "";

        // When
        SiretInvalideException exception = new SiretInvalideException(siretVide);

        // Then
        assertThat(exception.getMessage()).contains("SIRET invalide: ");
        assertThat(exception.getSiret()).isEqualTo(siretVide);
    }

    @Test
    @DisplayName("Doit être une RuntimeException")
    void shouldBeRuntimeException() {
        // Given
        SiretInvalideException exception = new SiretInvalideException("12345678901234");

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Doit conserver le message d'erreur original")
    void shouldPreserveOriginalErrorMessage() {
        // Given
        String siretInvalide = "ABC123";

        // When
        SiretInvalideException exception = new SiretInvalideException(siretInvalide);

        // Then
        assertThat(exception.getMessage()).isEqualTo("SIRET invalide: ABC123");
    }
}
