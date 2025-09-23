package com.bpifranceexec.exec.integrationTest.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
@CucumberContextConfiguration
@SpringBootTest(classes = com.bpifranceexec.exec.BpiExecApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeneficiaireSteps {

@Autowired
private TestRestTemplate restTemplate;

private ResponseEntity<String> response;
private final ObjectMapper objectMapper = new ObjectMapper();
private final Map<String, Long> entityIds = new HashMap<>();

// Helper method to create HttpEntity with JSON headers
private HttpEntity<String> createJsonEntity(String body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(body, headers);
}

@Given("Une entreprise {string} est enregistrée")
public void uneEntrepriseEstEnregistree(String nomEntreprise) throws JsonProcessingException {
    String requestBody = String.format("{\"entrepriseNom\": \"%s\", \"entrepriseSiret\": \"%s\"}", nomEntreprise, UUID.randomUUID().toString().substring(0, 14));
    ResponseEntity<String> postResponse = restTemplate.exchange("/api/entreprise", HttpMethod.POST, createJsonEntity(requestBody), String.class);
    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    JsonNode root = objectMapper.readTree(postResponse.getBody());
    entityIds.put("entreprise_id", root.get("entrepriseId").asLong());
}

@Given("Une personne physique {string} est enregistrée")
public void unePersonnePhysiqueEstEnregistree(String nomComplet) throws JsonProcessingException {
    String[] noms = nomComplet.split(" ");
    String requestBody = String.format("{\"personneNom\": \"%s\", \"personnePrenom\": \"%s\"}", noms[1], noms[0]);
    ResponseEntity<String> postResponse = restTemplate.exchange("/api/personne", HttpMethod.POST, createJsonEntity(requestBody), String.class);
    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    JsonNode root = objectMapper.readTree(postResponse.getBody());
    entityIds.put("personne_id", root.get("personneId").asLong());
}

@Given("Une entreprise {string} est enregistrée comme une entreprise fille")
public void uneEntrepriseEstEnregistreeCommeUneEntrepriseFille(String nomEntreprise) throws JsonProcessingException {
    String requestBody = String.format("{\"entrepriseNom\": \"%s\", \"entrepriseSiret\": \"%s\"}", nomEntreprise, UUID.randomUUID().toString().substring(0, 14));
    ResponseEntity<String> postResponse = restTemplate.exchange("/api/entreprise", HttpMethod.POST, createJsonEntity(requestBody), String.class);
    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    JsonNode root = objectMapper.readTree(postResponse.getBody());
    entityIds.put("entrepriseFille_id", root.get("entrepriseId").asLong());
}

@Given("Une entreprise mere {string} est enregistrée avec des bénéficiaires")
public void uneEntrepriseEstEnregistreeAvecDesBeneficiaires(String nomEntreprise) throws JsonProcessingException {
    // Step 1: Create and store IDs for each entity with unique keys.
    // Parent company
    uneEntrepriseEstEnregistree(nomEntreprise);
    Long entrepriseMereId = entityIds.get("entreprise_id");
    // Store the parent company's ID in a permanent key.
    entityIds.put("entrepriseMereId", entrepriseMereId);
    
    // Child company
    String entrepriseFilleNom = nomEntreprise + "Child";
    uneEntrepriseEstEnregistree(entrepriseFilleNom);
    Long entrepriseFilleId = entityIds.get("entreprise_id");
    
    // Person
    String personneNom = "John";
    String personnePrenom = "Doe";
    unePersonnePhysiqueEstEnregistree(personneNom + " " + personnePrenom);
    Long personnePhysiqueId = entityIds.get("personne_id");
    
    // Step 2: Build the request body with all three IDs and corresponding names.
    String requestBody = String.format("""
    {
      "entrepriseMere": {
        "entrepriseId": %d,
        "entrepriseNom": "%s",
        "entrepriseSiret": "98765432100000"
      },
      "personnePhysique": {
        "personneId": %d,
        "personneNom": "%s",
        "personnePrenom": "%s"
      },
      "entrepriseFille": {
        "entrepriseId": %d,
        "entrepriseNom": "%s",
        "entrepriseSiret": "12345678900000"
      },
      "pourcentage": 50
    }
    """, entrepriseMereId, nomEntreprise, personnePhysiqueId, personnePrenom, personneNom, entrepriseFilleId, entrepriseFilleNom);
    
    // Step 3: Make the POST request.
    restTemplate.exchange("/api/beneficiaire", HttpMethod.POST, createJsonEntity(requestBody), String.class);
}

@When("Je fais une requête GET sur {string}")
public void jeFaisUneRequeteGETSur(String url) {
    String finalUrl = url.replace("{entreprise_id}", entityIds.getOrDefault("entrepriseMereId", -1L).toString());
    this.response = restTemplate.getForEntity(finalUrl, String.class);
}

// Corrected method in BeneficiaireSteps.java
@When("Je fais une requête POST sur {string} avec le corps suivant")
public void jeFaisUneRequetePOSTSurAvecLeCorpsSuivant(String url, String body) {
    String finalBody = body.replace("{entreprise_id}", entityIds.getOrDefault("entreprise_id", -1L).toString())
            .replace("{personne_id}", entityIds.getOrDefault("personne_id", -1L).toString())
            .replace("{entrepriseFille_id}", entityIds.getOrDefault("entrepriseFille_id", -1L).toString());
    
    this.response = restTemplate.exchange(url, HttpMethod.POST, createJsonEntity(finalBody), String.class);
}

@Then("Je devrais recevoir une réponse avec le statut {int} {string}")
public void jeDevraisRecevoirUneReponseAvecLeStatut(int statusCode, String statusText) {
    assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
}

@Then("La réponse contient une entreprise avec le nom {string}")
public void laReponseContientUneEntrepriseAvecLeNom(String expectedName) throws JsonProcessingException {
    JsonNode root = objectMapper.readTree(response.getBody());
    assertEquals(expectedName, root.get("entrepriseNom").asText());
}

@Then("La réponse contient une personne avec le nom {string}")
public void laReponseContientUnePersonneAvecLeNom(String expectedName) throws JsonProcessingException {
    JsonNode root = objectMapper.readTree(response.getBody());
    String prenom = root.get("personnePrenom").asText();
    String nom = root.get("personneNom").asText();
    assertEquals(expectedName, prenom + " " + nom);
}

@Then("La réponse contient un bénéficiaire avec un pourcentage de détention de {int}")
public void laReponseContientUnBeneficiaireAvecUnPourcentageDeDetentionDe(int expectedPercentage) throws JsonProcessingException {
    JsonNode root = objectMapper.readTree(response.getBody());
    assertEquals(expectedPercentage, root.get("pourcentage").asInt());
}

@Then("La liste des bénéficiaires ne doit pas être vide")
public void laListeDesBeneficiairesNeDoitPasEtreVide() throws JsonProcessingException {
    JsonNode root = objectMapper.readTree(response.getBody());
    assertTrue(root.isArray());
    assertFalse(root.isEmpty());
}
}