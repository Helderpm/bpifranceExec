package com.bpifranceexec.exec.integrationTest.steps;

import com.bpifranceexec.exec.BpiExecApplication;
import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(classes = BpiExecApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeneficiaireSteps {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private ResponseEntity<?> response;
    private Entreprise entreprise;
    private PersonnePhysique personnePhysique;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Given("Une entreprise {string} est enregistrée")
    public void uneEntrepriseEstEnregistree(String nomEntreprise) {
        this.entreprise = restTemplate.postForEntity(
                "/api/entreprise",
                new Entreprise(null, nomEntreprise, UUID.randomUUID().toString().substring(0, 14)),
                Entreprise.class
        ).getBody();
        assertNotNull(this.entreprise);
    }
    
    @Given("Une personne physique {string} est enregistrée")
    public void unePersonnePhysiqueEstEnregistree(String nomComplet) {
        String[] noms = nomComplet.split(" ");
        this.personnePhysique = restTemplate.postForEntity(
                "/api/personne",
                new PersonnePhysique(null, noms[1], noms[0]),
                PersonnePhysique.class
        ).getBody();
        assertNotNull(this.personnePhysique);
    }
    
    @Given("Une entreprise {string} est enregistrée avec des bénéficiaires")
    public void uneEntrepriseEstEnregistreeAvecDesBeneficiaires(String nomEntreprise) {
        uneEntrepriseEstEnregistree(nomEntreprise);
        unePersonnePhysiqueEstEnregistree("John Doe");
        
        Beneficiaire beneficiaire = new Beneficiaire(null, this.entreprise, this.personnePhysique, null, 50);
        restTemplate.postForEntity("/api/beneficiaire", beneficiaire, Beneficiaire.class);
    }
    
    @When("Je fais une requête GET sur {string}")
    public void jeFaisUneRequeteGETSur(String url) {
        String finalUrl = url.replace("{entreprise_id}", this.entreprise != null ? this.entreprise.id().toString() : "");
        this.response = restTemplate.getForEntity(finalUrl, String.class);
    }
    
    @When("Je fais une requête POST sur {string} avec le corps suivant")
    public void jeFaisUneRequetePOSTSurAvecLeCorpsSuivant(String url, String body) throws JsonProcessingException {
        String finalBody = body.replace("{entreprise_id}", this.entreprise != null ? this.entreprise.id().toString() : "")
                .replace("{personne_id}", this.personnePhysique != null ? this.personnePhysique.id().toString() : "");
        
        // Convertir la chaîne JSON en un objet pour la requête
        JsonNode jsonNode = objectMapper.readTree(finalBody);
        
        this.response = restTemplate.postForEntity(url, jsonNode, String.class);
    }
    
    @Then("Je devrais recevoir une réponse avec le statut {int} {word}")
    public void jeDevraisRecevoirUneReponseAvecLeStatut(int statusCode, String statusText) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }
    
    @Then("La réponse contient une entreprise avec le nom {string}")
    public void laReponseContientUneEntrepriseAvecLeNom(String expectedName) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree((String) response.getBody());
        assertEquals(expectedName, root.get("nom").asText());
    }
    
    @Then("La réponse contient une personne avec le nom {string}")
    public void laReponseContientUnePersonneAvecLeNom(String expectedName) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree((String) response.getBody());
        assertEquals(expectedName.split(" ")[0], root.get("prenom").asText());
        assertEquals(expectedName.split(" ")[1], root.get("nom").asText());
    }
    
    @Then("La réponse contient un bénéficiaire avec un pourcentage de détention de {int}")
    public void laReponseContientUnBeneficiaireAvecUnPourcentageDeDetentionDe(int expectedPercentage) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree((String) response.getBody());
        assertEquals(expectedPercentage, root.get("pourcentageDetention").asInt());
    }
    
    @Then("La liste des bénéficiaires ne doit pas être vide")
    public void laListeDesBeneficiairesNeDoitPasEtreVide() throws JsonProcessingException {
        JsonNode root = objectMapper.readTree((String) response.getBody());
        assertTrue(root.isArray());
        assertFalse(root.isEmpty());
    }
}