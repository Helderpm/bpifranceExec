# src/test/resources/features/beneficiaire.feature
Feature: Gestion des entités et des bénéficiaires

  Scenario: Créer une nouvelle entreprise
    When Je fais une requête POST sur "/api/entreprise" avec le corps suivant
    """
    {
      "entrepriseNom": "BPI France",
      "entrepriseSiret": "98765432100000"
    }
    """
    Then Je devrais recevoir une réponse avec le statut 201 'Created'
    And La réponse contient une entreprise avec le nom "BPI France"

  Scenario: Créer une nouvelle personne physique
    When Je fais une requête POST sur "/api/personne" avec le corps suivant
      """
      {
        "personneNom": "Doe",
        "personnePrenom": "Jane"
      }
      """
    Then Je devrais recevoir une réponse avec le statut 201 'Created'
    And La réponse contient une personne avec le nom "Jane Doe"

  Scenario: Ajouter un nouveau bénéficiaire à une entreprise
    Given Une entreprise "Tech Solutions" est enregistrée
    And Une personne physique "Alice Dupont" est enregistrée
    And Une entreprise "Solutions Junior" est enregistrée comme une entreprise fille
    When Je fais une requête POST sur "/api/beneficiaire" avec le corps suivant
    """
    {
      "entrepriseMere": {
        "entrepriseId": "{entreprise_id}",
        "entrepriseNom": "Tech Solutions",
        "entrepriseSiret": "98765432100000"
      },
      "personnePhysique": {
        "personneId": "{personne_id}",
        "personneNom": "Dupont",
        "personnePrenom": "Alice"
      },
      "entrepriseFille": {
        "entrepriseId": "{entrepriseFille_id}",
        "entrepriseNom": "Solutions Junior",
        "entrepriseSiret": "12345678900000"
      },
      "pourcentage": 45
    }
    """
    Then Je devrais recevoir une réponse avec le statut 201 'Created'
    And La réponse contient un bénéficiaire avec un pourcentage de détention de 45

  Scenario: Récupérer les bénéficiaires d'une entreprise existante
    Given Une entreprise mere "ACME Corp" est enregistrée avec des bénéficiaires
    When Je fais une requête GET sur "/api/entreprise/{entreprise_id}/beneficiaires?type=all"
    Then Je devrais recevoir une réponse avec le statut 200 'OK'
    And La liste des bénéficiaires ne doit pas être vide

  Scenario: Récupérer les bénéficiaires d'une entreprise non existante
    When Je fais une requête GET sur "/api/entreprise/999999/beneficiaires"
    Then Je devrais recevoir une réponse avec le statut 204 'No Content'

