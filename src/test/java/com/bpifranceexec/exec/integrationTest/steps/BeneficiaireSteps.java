package com.bpifranceexec.exec.integrationTest.steps;

import com.bpifranceexec.exec.BpiExecApplication;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(classes = BpiExecApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeneficiaireSteps {

    @Autowired
    private TestRestTemplate restTemplate;
    
    private ResponseEntity<String> response;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Long> entityIds = new HashMap<>();
    
    @Given("Une entreprise {string} est enregistrée")
    public void uneEntrepriseEstEnregistree(String nomEntreprise) throws JsonProcessingException {
        String requestBody = String.format("{\"nom\": \"%s\", \"siret\": \"%s\"}", nomEntreprise, UUID.randomUUID().toString().substring(0, 14));
        ResponseEntity<String> postResponse = restTemplate.postForEntity("/api/entreprise", requestBody, String.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        JsonNode root = objectMapper.readTree(postResponse.getBody());
        entityIds.put("entreprise_id", root.get("id").asLong());
    }
    
    @Given("Une personne physique {string} est enregistrée")
    public void unePersonnePhysiqueEstEnregistree(String nomComplet) throws JsonProcessingException {
        String[] noms = nomComplet.split(" ");
        String requestBody = String.format("{\"nom\": \"%s\", \"prenom\": \"%s\"}", noms[1], noms[0]);
        ResponseEntity<String> postResponse = restTemplate.postForEntity("/api/personne", requestBody, String.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        JsonNode root = objectMapper.readTree(postResponse.getBody());
        entityIds.put("personne_id", root.get("id").asLong());
    }
    
    @Given("Une entreprise {string} est enregistrée avec des bénéficiaires")
    public void uneEntrepriseEstEnregistreeAvecDesBeneficiaires(String nomEntreprise) throws JsonProcessingException {
        uneEntrepriseEstEnregistree(nomEntreprise);
        unePersonnePhysiqueEstEnregistree("John Doe");
        
        String requestBody = String.format("{\"entrepriseMereId\": %d, \"personnePhysiqueId\": %d, \"pourcentageDetention\": 50}",
                entityIds.get("entreprise_id"),
                entityIds.get("personne_id"));
        restTemplate.postForEntity("/api/beneficiaire", requestBody, String.class);
    }
    
    @When("Je fais une requête GET sur {string}")
    public void jeFaisUneRequeteGETSur(String url) {
        String finalUrl = url.replace("{entreprise_id}", entityIds.getOrDefault("entreprise_id", -1L).toString());
        this.response = restTemplate.getForEntity(finalUrl, String.class);
    }
    
    @When("Je fais une requête POST sur {string} avec le corps suivant")
    public void jeFaisUneRequetePOSTSurAvecLeCorpsSuivant(String url, String body) {
        String finalBody = body.replace("{entreprise_id}", entityIds.getOrDefault("entreprise_id", -1L).toString())
                .replace("{personne_id}", entityIds.getOrDefault("personne_id", -1L).toString());
        
        this.response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(finalBody), String.class);
    }
    
    @Then("Je devrais recevoir une réponse avec le statut {int} {string}")
    public void jeDevraisRecevoirUneReponseAvecLeStatut(int statusCode, String statusText) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }
    
    @Then("La réponse contient une entreprise avec le nom {string}")
    public void laReponseContientUneEntrepriseAvecLeNom(String expectedName) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        assertEquals(expectedName, root.get("nom").asText());
    }
    
    @Then("La réponse contient une personne avec le nom {string}")
    public void laReponseContientUnePersonneAvecLeNom(String expectedName) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        String prenom = root.get("prenom").asText();
        String nom = root.get("nom").asText();
        assertEquals(expectedName, prenom + " " + nom);
    }
    
    @Then("La réponse contient un bénéficiaire avec un pourcentage de détention de {int}")
    public void laReponseContientUnBeneficiaireAvecUnPourcentageDeDetentionDe(int expectedPercentage) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        assertEquals(expectedPercentage, root.get("pourcentageDetention").asInt());
    }
    
    @Then("La liste des bénéficiaires ne doit pas être vide")
    public void laListeDesBeneficiairesNeDoitPasEtreVide() throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        assertTrue(root.isArray());
        assertFalse(root.isEmpty());
    }
    
}