package com.bpifranceexec.exec.exposition;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.in.GestionBeneficiairePort;
import com.bpifranceexec.exec.exposition.rest.BeneficiaireController;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import com.bpifranceexec.exec.exposition.rest.mapper.BeneficiaireDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.EntrepriseDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.PersonnePhysiqueDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeneficiaireControllerTest {
    
    @InjectMocks
    private BeneficiaireController beneficiaireController;
    
    @Mock
    private EntrepriseDtoMapper entrepriseMapper;
    @Mock
    private PersonnePhysiqueDtoMapper personneMapper;
    @Mock
    private BeneficiaireDtoMapper beneficiaireMapper;
    
    @Mock
    private GestionBeneficiairePort gestionBeneficiairePort;
    
    private EntrepriseDto entrepriseDto;
    private Entreprise entreprise;
    private PersonnePhysiqueDto personneDto;
    private PersonnePhysique personne;
    private BeneficiaireDto beneficiaireDto;
    private Beneficiaire beneficiaire;
    
    @BeforeEach
    void setUp() {
        entrepriseDto = new EntrepriseDto(1L, "TestCorp", "123456789");
        entreprise = new Entreprise(1L, "TestCorp", "123456789");
        personneDto = new PersonnePhysiqueDto(2L, "Doe", "John");
        personne = new PersonnePhysique(2L, "Doe", "John");
        beneficiaireDto = new BeneficiaireDto(3L, entrepriseDto, personneDto, null, 50);
        beneficiaire = new Beneficiaire(3L, entreprise, personne, null, 50);
    }
    
    @Test
    void addEntreprise_shouldReturn201Created_whenSuccessful() {
        // Arrange
        when(entrepriseMapper.toDomain(any(EntrepriseDto.class))).thenReturn(entreprise);
        when(gestionBeneficiairePort.creerEntreprise(any(Entreprise.class))).thenReturn(Optional.of(entreprise));
        when(entrepriseMapper.toDto(any(Entreprise.class))).thenReturn(entrepriseDto);
        
        // Act
        ResponseEntity<EntrepriseDto> response = beneficiaireController.addEntreprise(entrepriseDto);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(entrepriseDto, response.getBody());
    }
    
    @Test
    void addEntreprise_shouldReturn500InternalServerError_whenServiceFails() {
        // Arrange
        when(entrepriseMapper.toDomain(any(EntrepriseDto.class))).thenReturn(entreprise);
        when(gestionBeneficiairePort.creerEntreprise(any(Entreprise.class))).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<EntrepriseDto> response = beneficiaireController.addEntreprise(entrepriseDto);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    // --- New Test Cases Below ---
    
    @Test
    void addPersonne_shouldReturn201Created_whenSuccessful() {
        // Arrange
        when(personneMapper.toDomain(any(PersonnePhysiqueDto.class))).thenReturn(personne);
        when(gestionBeneficiairePort.creerPersonnePhysique(any(PersonnePhysique.class))).thenReturn(Optional.of(personne));
        when(personneMapper.toDto(any(PersonnePhysique.class))).thenReturn(personneDto);
        
        // Act
        ResponseEntity<PersonnePhysiqueDto> response = beneficiaireController.addPersonne(personneDto);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personneDto, response.getBody());
    }
    
    @Test
    void addPersonne_shouldReturn500InternalServerError_whenServiceFails() {
        // Arrange
        when(personneMapper.toDomain(any(PersonnePhysiqueDto.class))).thenReturn(personne);
        when(gestionBeneficiairePort.creerPersonnePhysique(any(PersonnePhysique.class))).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<PersonnePhysiqueDto> response = beneficiaireController.addPersonne(personneDto);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    void addBeneficiaire_shouldReturn201Created_whenSuccessful() {
        // Arrange
        when(beneficiaireMapper.toDomain(any(BeneficiaireDto.class))).thenReturn(beneficiaire);
        when(gestionBeneficiairePort.ajouterBeneficiaire(any(Beneficiaire.class))).thenReturn(Optional.of(beneficiaire));
        when(beneficiaireMapper.toDto(any(Beneficiaire.class))).thenReturn(beneficiaireDto);
        
        // Act
        ResponseEntity<BeneficiaireDto> response = beneficiaireController.addBeneficiaire(beneficiaireDto);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(beneficiaireDto, response.getBody());
    }
    
    @Test
    void addBeneficiaire_shouldReturn500InternalServerError_whenServiceFails() {
        // Arrange
        when(beneficiaireMapper.toDomain(any(BeneficiaireDto.class))).thenReturn(beneficiaire);
        when(gestionBeneficiairePort.ajouterBeneficiaire(any(Beneficiaire.class))).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<BeneficiaireDto> response = beneficiaireController.addBeneficiaire(beneficiaireDto);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    void getBeneficiaires_shouldReturn200Ok_withBeneficiaries() {
        // Arrange
        List<Beneficiaire> beneficiaries = Collections.singletonList(beneficiaire);
        List<BeneficiaireDto> beneficiaryDtos = Collections.singletonList(beneficiaireDto);
        
        when(gestionBeneficiairePort.recupererBeneficiaires(eq(1L), eq("all"))).thenReturn(beneficiaries);
        when(beneficiaireMapper.toDto(any(Beneficiaire.class))).thenReturn(beneficiaryDtos.getFirst());
        
        // Act
        ResponseEntity<List<BeneficiaireDto>> response = beneficiaireController.getBeneficiaires(1L, "all");
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(beneficiaryDtos.getFirst(), response.getBody().getFirst());
    }
    
    @Test
    void getBeneficiaires_shouldReturn204NoContent_whenNoBeneficiariesFound() {
        // Arrange
        when(gestionBeneficiairePort.recupererBeneficiaires(any(Long.class), any(String.class)))
                .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<List<BeneficiaireDto>> response = beneficiaireController.getBeneficiaires(999L, "all");
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
