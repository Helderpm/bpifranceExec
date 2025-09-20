package com.bpifranceexec.exec.exposition;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.in.GestionBeneficiairePort;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import com.bpifranceexec.exec.exposition.rest.mapper.BeneficiaireDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.EntrepriseDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.PersonnePhysiqueDtoMapper;
import com.bpifranceexec.exec.exposition.rest.BeneficiaireController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficiaireController.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BeneficiaireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GestionBeneficiairePort gestionBeneficiairePort;

    @Autowired
    private EntrepriseDtoMapper entrepriseMapper;

    @Autowired
    private PersonnePhysiqueDtoMapper personneMapper;
    
    @Autowired
    private BeneficiaireDtoMapper beneficiaireMapper;

@Test
void addEntreprise_shouldReturn201() throws Exception {
    EntrepriseDto inputDto = new EntrepriseDto(null, "Acme Corp", "12345678901234");
    Entreprise domainInput = new Entreprise(null, "Acme Corp", "12345678901234");
    Entreprise savedDomain = new Entreprise(UUID.randomUUID(), "Acme Corp", "12345678901234");
    EntrepriseDto savedDto = new EntrepriseDto(savedDomain.id(), savedDomain.nom(), savedDomain.siret());

    when(entrepriseMapper.toDomain(inputDto)).thenReturn(domainInput);
    when(gestionBeneficiairePort.creerEntreprise(domainInput)).thenReturn(Optional.of(savedDomain));
    when(entrepriseMapper.toDto(savedDomain)).thenReturn(savedDto);

    mockMvc.perform(post("/api/entreprise")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedDto.id().toString()))
            .andExpect(jsonPath("$.nom").value("Acme Corp"))
            .andExpect(jsonPath("$.siret").value("12345678901234"));
}

@Test
void addEntreprise_shouldReturn500_whenServiceEmpty() throws Exception {
    EntrepriseDto inputDto = new EntrepriseDto(null, "Bad Corp", "00000000000000");
    Entreprise domainInput = new Entreprise(null, "Bad Corp", "00000000000000");

    when(entrepriseMapper.toDomain(inputDto)).thenReturn(domainInput);
    when(gestionBeneficiairePort.creerEntreprise(domainInput)).thenReturn(Optional.empty());

    mockMvc.perform(post("/api/entreprise")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isInternalServerError());
}

@Test
void addPersonne_shouldReturn201() throws Exception {
    PersonnePhysiqueDto inputDto = new PersonnePhysiqueDto(null, "Doe", "John");
    PersonnePhysique domainInput = new PersonnePhysique(null, "Doe", "John");
    PersonnePhysique savedDomain = new PersonnePhysique(UUID.randomUUID(), "Doe", "John");
    PersonnePhysiqueDto savedDto = new PersonnePhysiqueDto(savedDomain.id(), savedDomain.nom(), savedDomain.prenom());

    when(personneMapper.toDomain(inputDto)).thenReturn(domainInput);
    when(gestionBeneficiairePort.creerPersonnePhysique(domainInput)).thenReturn(Optional.of(savedDomain));
    when(personneMapper.toDto(savedDomain)).thenReturn(savedDto);

    mockMvc.perform(post("/api/personne")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedDto.id().toString()))
            .andExpect(jsonPath("$.nom").value("Doe"))
            .andExpect(jsonPath("$.prenom").value("John"));
}

@Test
void addBeneficiaire_shouldReturn201() throws Exception {
    UUID entrepriseMereId = UUID.randomUUID();
    UUID personneId = UUID.randomUUID();
    BeneficiaireDto inputDto = new BeneficiaireDto(null, entrepriseMereId, personneId, null, 60);

    Beneficiaire domainInput = new Beneficiaire(
            null,
            new Entreprise(entrepriseMereId, null, null),
            new PersonnePhysique(personneId, null, null),
            null,
            60
    );
    Beneficiaire savedDomain = new Beneficiaire(UUID.randomUUID(), domainInput.entrepriseMere(), domainInput.personnePhysique(), null, 60);
    BeneficiaireDto savedDto = new BeneficiaireDto(savedDomain.id(), entrepriseMereId, personneId, null, 60);

    when(beneficiaireMapper.toDomain(inputDto)).thenReturn(domainInput);
    when(gestionBeneficiairePort.ajouterBeneficiaire(domainInput)).thenReturn(Optional.of(savedDomain));
    when(beneficiaireMapper.toDto(savedDomain)).thenReturn(savedDto);

    mockMvc.perform(post("/api/beneficiaire")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedDto.id().toString()))
            .andExpect(jsonPath("$.entrepriseMereId").value(entrepriseMereId.toString()))
            .andExpect(jsonPath("$.personnePhysiqueId").value(personneId.toString()))
            .andExpect(jsonPath("$.pourcentageDetention").value(60));
}

@Test
void getBeneficiaires_shouldReturn200_withList() throws Exception {
    UUID entrepriseId = UUID.randomUUID();
    Beneficiaire domain = new Beneficiaire(UUID.randomUUID(), new Entreprise(entrepriseId, null, null), new PersonnePhysique(UUID.randomUUID(), null, null), null, 30);
    BeneficiaireDto dto = new BeneficiaireDto(domain.id(), entrepriseId, domain.personnePhysique().id(), null, 30);

    when(gestionBeneficiairePort.recupererBeneficiaires(entrepriseId, "all")).thenReturn(List.of(domain));
    when(beneficiaireMapper.toDto(domain)).thenReturn(dto);

    mockMvc.perform(get("/api/entreprise/{entrepriseId}/beneficiaires", entrepriseId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(dto.id().toString()))
            .andExpect(jsonPath("$[0].entrepriseMereId").value(entrepriseId.toString()))
            .andExpect(jsonPath("$[0].pourcentageDetention").value(30));
}

@Test
void getBeneficiaires_shouldReturn204_whenEmpty() throws Exception {
    UUID entrepriseId = UUID.randomUUID();
    when(gestionBeneficiairePort.recupererBeneficiaires(entrepriseId, "all")).thenReturn(List.of());

    mockMvc.perform(get("/api/entreprise/{entrepriseId}/beneficiaires", entrepriseId))
            .andExpect(status().isNoContent());
}

}