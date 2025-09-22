package com.bpifranceexec.exec;

import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeneficiaireControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void addEntreprise_shouldReturn201_onSuccess() throws Exception {
        // Arrange
        EntrepriseDto entrepriseDto = new EntrepriseDto(null, "NewCorp", "987654321");
        
        // Act & Assert
        mockMvc.perform(post("/api/entreprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrepriseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.entrepriseDtoNom", is("NewCorp")));
    }
    
    @Test
    void addPersonne_shouldReturn201_onSuccess() throws Exception {
        // Arrange
        PersonnePhysiqueDto personneDto = new PersonnePhysiqueDto(null, "Test", "User");
        
        // Act & Assert
        mockMvc.perform(post("/api/personne")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personneDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personneDtoNom", is("Test")));
    }

@Test
@Transactional
@Sql("/db/data.sql")
void addBeneficiaire_shouldReturn201_onSuccess() throws Exception {
    // Arrange - Use IDs that are already present in the data.sql script
    // These IDs (1, 2, 3) are loaded by Spring Boot automatically
    EntrepriseDto entrepriseMereDto = new EntrepriseDto(1L, null, null);
    EntrepriseDto entrepriseFilleDto = new EntrepriseDto(2L, null, null);
    PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto(3L, null, null);
    
    // BeneficiaireDto for the POST request
    BeneficiaireDto beneficiaireDto = new BeneficiaireDto(null, entrepriseMereDto, personnePhysiqueDto, entrepriseFilleDto, 75);
    
    // Act & Assert
    mockMvc.perform(post("/api/beneficiaire")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beneficiaireDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.pourcentageDetentionDto", is(75)));
}
    @Test
    @Transactional
    @Sql("/db/data.sql") // This will execute the script before this test
    void getBeneficiaires_shouldReturn200_withData_whenBeneficiariesExist() throws Exception {
        // This test requires a valid `entrepriseId` that has associated beneficiaries in the test database.
        // For a true integration test, you would pre-populate the H2 database.
        
        // Arrange (assuming test data exists for entrepriseId=1)
        Long existingEntrepriseId = 1L;
        
        // Act & Assert
        mockMvc.perform(get("/api/entreprise/{entrepriseId}/beneficiaires", existingEntrepriseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

@Test
@Transactional
@Sql("/db/data.sql")
    void getBeneficiaires_shouldReturn204_whenNoBeneficiariesFound() throws Exception {
        // This test requires an `entrepriseId` that exists but has no beneficiaries.
        // For a true integration test, you would pre-populate the H2 database.
        
        // Arrange (assuming test data exists for entrepriseId=99 but with no beneficiaries)
        Long emptyEntrepriseId = 99L;
        
        // Act & Assert
        mockMvc.perform(get("/api/entreprise/{entrepriseId}/beneficiaires", emptyEntrepriseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
