package com.bpifranceexec.exec.infrastructure.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour {@link RepositoryOperationFailedException}.
 * <p>
 * Ces tests vérifient le comportement de l'exception d'opération repository échouée
 * dans différents scénarios d'utilisation.
 * </p>
 */
@DisplayName("Tests de RepositoryOperationFailedException")
class RepositoryOperationFailedExceptionTest {

    @Test
    @DisplayName("Doit créer l'exception avec l'opération et le type d'entité")
    void shouldCreateExceptionWithOperationAndEntityType() {
        // Given
        String operation = "save";
        String entityType = "Entreprise";

        // When
        RepositoryOperationFailedException exception = new RepositoryOperationFailedException(operation, entityType);

        // Then
        assertThat(exception.getMessage()).contains("Opération 'save' échouée pour l'entité: Entreprise");
        assertThat(exception.getOperation()).isEqualTo(operation);
        assertThat(exception.getEntityType()).isEqualTo(entityType);
    }

    @Test
    @DisplayName("Doit créer l'exception avec l'opération, le type d'entité et la cause")
    void shouldCreateExceptionWithOperationEntityTypeAndCause() {
        // Given
        String operation = "delete";
        String entityType = "Beneficiaire";
        RuntimeException cause = new RuntimeException("Constraint violation");

        // When
        RepositoryOperationFailedException exception = new RepositoryOperationFailedException(operation, entityType, cause);

        // Then
        assertThat(exception.getMessage()).contains("Opération 'delete' échouée pour l'entité: Beneficiaire");
        assertThat(exception.getOperation()).isEqualTo(operation);
        assertThat(exception.getEntityType()).isEqualTo(entityType);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("Doit gérer une opération vide")
    void shouldHandleEmptyOperation() {
        // Given
        String operationVide = "";
        String entityType = "PersonnePhysique";

        // When
        RepositoryOperationFailedException exception = new RepositoryOperationFailedException(operationVide, entityType);

        // Then
        assertThat(exception.getMessage()).contains("Opération '' échouée pour l'entité: PersonnePhysique");
        assertThat(exception.getOperation()).isEqualTo(operationVide);
        assertThat(exception.getEntityType()).isEqualTo(entityType);
    }

    @Test
    @DisplayName("Doit gérer un type d'entité vide")
    void shouldHandleEmptyEntityType() {
        // Given
        String operation = "find";
        String entityTypeVide = "";

        // When
        RepositoryOperationFailedException exception = new RepositoryOperationFailedException(operation, entityTypeVide);

        // Then
        assertThat(exception.getMessage()).contains("Opération 'find' échouée pour l'entité: ");
        assertThat(exception.getOperation()).isEqualTo(operation);
        assertThat(exception.getEntityType()).isEqualTo(entityTypeVide);
    }

    @Test
    @DisplayName("Doit être une RuntimeException")
    void shouldBeRuntimeException() {
        // Given
        RepositoryOperationFailedException exception = new RepositoryOperationFailedException("update", "Test");

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Doit conserver le message d'erreur original")
    void shouldPreserveOriginalErrorMessage() {
        // Given
        String operation = "query";
        String entityType = "Beneficiaire";

        // When
        RepositoryOperationFailedException exception = new RepositoryOperationFailedException(operation, entityType);

        // Then
        assertThat(exception.getMessage()).isEqualTo("Opération 'query' échouée pour l'entité: Beneficiaire");
    }
}
