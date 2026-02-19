package com.bpifranceexec.exec.exposition;

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
        EntrepriseDto entrepriseDto = new EntrepriseDto(null, "ValidCorp", "12345678901234");
        
        // Act & Assert
        mockMvc.perform(post("/api/entreprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrepriseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.entrepriseNom", is("ValidCorp")));
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
                .andExpect(jsonPath("$.personneNom", is("Test")));
    }

    @Test
    @Transactional
    @Sql("/db/data.sql")
    void addBeneficiaire_shouldReturn201_onSuccess() throws Exception {
        // Arrange - Create fully populated DTOs to match the expected JSON structure
        EntrepriseDto entrepriseMereDto = new EntrepriseDto(1L, "TestCorp", "12345678901234");
        PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto(3L, "Doe", "John");
        EntrepriseDto entrepriseFilleDto = new EntrepriseDto(2L, "ChildCorp", "98765432109876");
        
        // BeneficiaireDto for the POST request
        BeneficiaireDto beneficiaireDto = new BeneficiaireDto(null, entrepriseMereDto, personnePhysiqueDto, entrepriseFilleDto, 75);
        
        mockMvc.perform(post("/api/beneficiaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beneficiaireDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pourcentage", is(75)))
                .andExpect(jsonPath("$.beneficiaireId", is(notNullValue())))
                .andExpect(jsonPath("$.entrepriseMere", is(notNullValue())));
        
    }

    @Test
    @Transactional
    @Sql("/db/data.sql")
    void getBeneficiaires_shouldReturn200_withData_whenBeneficiariesExist() throws Exception {
        // Arrange (assuming the data.sql populates data for entrepriseId=1)
        Long existingEntrepriseId = 1L;
        
        // Act & Assert
        mockMvc.perform(get("/api/entreprise/{entrepriseId}/beneficiaires", existingEntrepriseId)
                        .queryParam("type", "all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @Transactional
    @Sql("/db/data.sql")
    void getBeneficiaires_shouldReturn204_whenNoBeneficiariesFound() throws Exception {
        // Arrange (assuming the data.sql populates data for entrepriseId=99 with no beneficiaries)
        Long emptyEntrepriseId = 99L;
        
        // Act & Assert
        mockMvc.perform(get("/api/entreprise/{entrepriseId}/beneficiaires", emptyEntrepriseId)
                        .queryParam("type", "all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
