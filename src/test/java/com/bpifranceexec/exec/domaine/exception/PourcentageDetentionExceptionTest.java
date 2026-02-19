package com.bpifranceexec.exec.domaine.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour {@link PourcentageDetentionException}.
 * <p>
 * Ces tests vérifient le comportement de l'exception pourcentage de détention invalide
 * dans différents scénarios d'utilisation.
 * </p>
 */
@DisplayName("Tests de PourcentageDetentionException")
class PourcentageDetentionExceptionTest {

    @Test
    @DisplayName("Doit créer l'exception avec le pourcentage invalide")
    void shouldCreateExceptionWithInvalidPercentage() {
        // Given
        int pourcentageInvalide = 150;

        // When
        PourcentageDetentionException exception = new PourcentageDetentionException(pourcentageInvalide);

        // Then
        assertThat(exception.getMessage()).contains("Pourcentage de détention invalide: 150%");
        assertThat(exception.getPourcentage()).isEqualTo(pourcentageInvalide);
    }

    @Test
    @DisplayName("Doit gérer un pourcentage négatif")
    void shouldHandleNegativePercentage() {
        // Given
        int pourcentageNegatif = -10;

        // When
        PourcentageDetentionException exception = new PourcentageDetentionException(pourcentageNegatif);

        // Then
        assertThat(exception.getMessage()).contains("Pourcentage de détention invalide: -10%");
        assertThat(exception.getPourcentage()).isEqualTo(pourcentageNegatif);
    }

    @Test
    @DisplayName("Doit gérer un pourcentage zéro")
    void shouldHandleZeroPercentage() {
        // Given
        int pourcentageZero = 0;

        // When
        PourcentageDetentionException exception = new PourcentageDetentionException(pourcentageZero);

        // Then
        assertThat(exception.getMessage()).contains("Pourcentage de détention invalide: 0%");
        assertThat(exception.getPourcentage()).isEqualTo(pourcentageZero);
    }

    @Test
    @DisplayName("Doit gérer un pourcentage de 100")
    void shouldHandleHundredPercentage() {
        // Given
        int pourcentageCent = 100;

        // When
        PourcentageDetentionException exception = new PourcentageDetentionException(pourcentageCent);

        // Then
        assertThat(exception.getMessage()).contains("Pourcentage de détention invalide: 100%");
        assertThat(exception.getPourcentage()).isEqualTo(pourcentageCent);
    }

    @Test
    @DisplayName("Doit être une RuntimeException")
    void shouldBeRuntimeException() {
        // Given
        PourcentageDetentionException exception = new PourcentageDetentionException(50);

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Doit conserver le message d'erreur original")
    void shouldPreserveOriginalErrorMessage() {
        // Given
        int pourcentageInvalide = 75;

        // When
        PourcentageDetentionException exception = new PourcentageDetentionException(pourcentageInvalide);

        // Then
        assertThat(exception.getMessage()).isEqualTo("Pourcentage de détention invalide: 75%");
    }
}
