package com.bpifranceexec.exec.infrastructure.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour {@link DatabaseConnectionException}.
 * <p>
 * Ces tests vérifient le comportement de l'exception de connexion base de données
 * dans différents scénarios d'utilisation.
 * </p>
 */
@DisplayName("Tests de DatabaseConnectionException")
class DatabaseConnectionExceptionTest {

    @Test
    @DisplayName("Doit créer l'exception avec l'opération")
    void shouldCreateExceptionWithOperation() {
        // Given
        String operation = "sauvegarde";

        // When
        DatabaseConnectionException exception = new DatabaseConnectionException(operation);

        // Then
        assertThat(exception.getMessage()).contains("Erreur de connexion base de données lors de: sauvegarde");
        assertThat(exception.getOperation()).isEqualTo(operation);
    }

    @Test
    @DisplayName("Doit créer l'exception avec l'opération et la cause")
    void shouldCreateExceptionWithOperationAndCause() {
        // Given
        String operation = "recherche";
        RuntimeException cause = new RuntimeException("Timeout connexion");

        // When
        DatabaseConnectionException exception = new DatabaseConnectionException(operation, cause);

        // Then
        assertThat(exception.getMessage()).contains("Erreur de connexion base de données lors de: recherche");
        assertThat(exception.getOperation()).isEqualTo(operation);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("Doit gérer une opération vide")
    void shouldHandleEmptyOperation() {
        // Given
        String operationVide = "";

        // When
        DatabaseConnectionException exception = new DatabaseConnectionException(operationVide);

        // Then
        assertThat(exception.getMessage()).contains("Erreur de connexion base de données lors de: ");
        assertThat(exception.getOperation()).isEqualTo(operationVide);
    }

    @Test
    @DisplayName("Doit gérer une opération null")
    void shouldHandleNullOperation() {
        // Given
        String operationNull = null;

        // When
        DatabaseConnectionException exception = new DatabaseConnectionException(operationNull);

        // Then
        assertThat(exception.getMessage()).contains("Erreur de connexion base de données lors de: null");
        assertThat(exception.getOperation()).isNull();
    }

    @Test
    @DisplayName("Doit être une RuntimeException")
    void shouldBeRuntimeException() {
        // Given
        DatabaseConnectionException exception = new DatabaseConnectionException("test");

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Doit conserver le message d'erreur original")
    void shouldPreserveOriginalErrorMessage() {
        // Given
        String operation = "suppression";

        // When
        DatabaseConnectionException exception = new DatabaseConnectionException(operation);

        // Then
        assertThat(exception.getMessage()).isEqualTo("Erreur de connexion base de données lors de: suppression");
    }
}
